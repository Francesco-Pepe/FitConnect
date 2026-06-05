package dao.trainingplan;

import eng.DBConnection;
import eng.ExerciseSerializer;
import exception.DAOException;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBTrainingPlanDAO extends TrainingPlanDAO {

    private Connection conn() {
        return DBConnection.getInstance().getConnection();
    }

    @Override
    public void save(TrainingPlan plan) {
        Connection c = conn();
        String sqlPlan = """
            INSERT INTO training_plan (athlete_email, creation_date, expiration_date)
            VALUES (?, ?, ?)
            """;
        try {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sqlPlan, Statement.RETURN_GENERATED_KEYS)) {
                //key is athlete email
                ps.setString(1, fetchKey(plan));
                ps.setDate(2, Date.valueOf(plan.getCreationDate()));
                ps.setDate(3, Date.valueOf(plan.getExpirationDate()));
                ps.executeUpdate();

                int planId;
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) throw new DAOException("Nessun ID generato per training_plan");
                    planId = keys.getInt(1);
                }
                // dovendo modificare 3 tabelle è necessario compiere le varie azioni atomicamente
                for (Exercise ex : plan.getExercises()) {
                    saveExercise(c, planId, ex);
                }
            }
            c.commit();
        } catch (SQLException e) {
            try { c.rollback(); } catch (SQLException ex) { /* ignore */ }
            throw new DAOException("Errore DB save training_plan", e);
        } finally {
            try { c.setAutoCommit(true); } catch (SQLException e) { /* ignore */ }
        }
    }

    private void saveExercise(Connection c, int planId, Exercise ex) throws SQLException {
        List<String> techniques = ExerciseSerializer.extractTechniques(ex);
        String sqlEx = """
            INSERT INTO plan_exercise (plan_id, name, target, equipment, sets, reps)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement ps = c.prepareStatement(sqlEx, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, planId);
            ps.setString(2, ex.getName());
            ps.setString(3, ex.getTarget());
            ps.setString(4, ex.getEquipment());
            ps.setInt(5, ex.getSets());
            ps.setInt(6, ex.getReps());
            ps.executeUpdate();

            if (techniques.isEmpty()) return;

            int exerciseId;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) return;
                exerciseId = keys.getInt(1);
            }

            String sqlTech = "INSERT INTO exercise_technique (exercise_id, technique) VALUES (?, ?)";
            try (PreparedStatement psTech = c.prepareStatement(sqlTech)) {
                psTech.setInt(1, exerciseId);
                for (String technique : techniques) {
                    psTech.setString(2, technique);
                    psTech.addBatch();
                }
                psTech.executeBatch();
            }
        }
    }

    @Override
    public TrainingPlan searchByAthlete(String athleteEmail) {
        Connection c = conn();
        String sql = """
            SELECT id, creation_date, expiration_date, athlete_email
            FROM training_plan tp
            WHERE tp.athlete_email = ?
            """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, athleteEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // will overwrite current rs, but only one plan in database for an athlete
                    return buildPlan(rs, fetchExercises(c, rs.getInt("id")));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB searchByAthlete", e);
        }
    }



    // Carica gli esercizi (con tecniche) dato un plan_id
    private List<Exercise> fetchExercises(Connection c, int planId) throws SQLException {
        String sql = """
            SELECT pe.id, pe.name, pe.target, pe.equipment, pe.sets, pe.reps
            FROM plan_exercise pe
            WHERE pe.plan_id = ?
            """;
        List<Exercise> exercises = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, planId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Exercise ex = new BaseExercise(
                            rs.getString("name"),
                            rs.getInt("reps"),
                            rs.getInt("sets"),
                            rs.getString("equipment"),
                            rs.getString("target")
                    );
                    ex = applyTechniques(c, ex, rs.getInt("id"));
                    exercises.add(ex);
                }
            }
        }
        return exercises;
    }

    // Riapplica i decorator nell'ordine in cui sono stati salvati
    private Exercise applyTechniques(Connection c, Exercise ex, int exerciseId) throws SQLException {
        String sql = "SELECT technique FROM exercise_technique WHERE exercise_id = ?";
        List<String> techniques = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, exerciseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    techniques.add(rs.getString("technique"));
                }
            }
        }
        return ExerciseSerializer.applyTechniques(ex, techniques);
    }
    //no Athlete because th AthleteService will link Athlete and Plan
    private TrainingPlan buildPlan(ResultSet rs, List<Exercise> exercises) throws SQLException {
        return new TrainingPlan(
                null,
                rs.getDate("creation_date").toLocalDate(),
                rs.getDate("expiration_date").toLocalDate(),
                exercises
        );
    }
}