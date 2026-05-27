package dao.trainingplan;

import eng.DBConnection;
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
        String sqlPlan = """
            INSERT INTO training_plan (athlete_email, pt_email, creation_date, expiration_date)
            VALUES (?, ?, ?, ?)
            """;
        try (PreparedStatement ps = conn().prepareStatement(sqlPlan, Statement.RETURN_GENERATED_KEYS)) {
            conn().setAutoCommit(false);
            ps.setString(1, plan.getClient());
            ps.setString(2, plan.getCreator());
            ps.setDate(3, Date.valueOf(plan.getCreationDate()));
            ps.setDate(4, Date.valueOf(plan.getExpirationDate()));
            ps.executeUpdate();

            int planId;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) throw new DAOException("Nessun ID generato per training_plan");
                planId = keys.getInt(1);
            }
            for (Exercise ex : plan.getExercises()) {
                saveExercise(planId, ex);
            }
            //dovendo modificare 3 tabelle è necessario compiere le varie azioni atomicamente
            conn().commit();
            addToCache(plan);
        } catch (SQLException e) {
            try {
                conn().rollback();
            } catch (SQLException ex) {
                //ignore
            }
            throw new DAOException("Errore DB save training_plan", e);
        }
        finally {
            try {conn().setAutoCommit(true);} catch (SQLException e) {
                //ignore
            }
        }
    }

    private void saveExercise(int planId, Exercise ex) throws SQLException {
        Exercise current = ex;
        List<String> techniques = new ArrayList<>();
        while (current instanceof ExerciseDecorator ed) {
            if (current instanceof DropSetDecorator)       techniques.add("DROP_SET");
            else if (current instanceof RestPauseDecorator)     techniques.add("REST_PAUSE");
            else if (current instanceof SlowEccentricDecorator) techniques.add("SLOW_ECCENTRIC");
            else if (current instanceof IsometricPauseDecorator)techniques.add("ISOMETRIC_PAUSE");
            else if (current instanceof ForcedRepsDecorator)    techniques.add("FORCED_REPS");
            current = ed.getWrapperExercise();
        }

        String sqlEx = """
            INSERT INTO plan_exercise (plan_id, name, target, equipment, sets, reps)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement ps = conn().prepareStatement(sqlEx, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, planId);
            ps.setString(2, current.getName());
            ps.setString(3, current.getTarget());
            ps.setString(4, current.getEquipment());
            ps.setInt(5, current.getSets());
            ps.setInt(6, current.getReps());
            ps.executeUpdate();

            if (techniques.isEmpty()) return;

            int exerciseId;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) return;
                exerciseId = keys.getInt(1);
            }

            String sqlTech = "INSERT INTO exercise_technique (exercise_id, technique) VALUES (?, ?)";
            try (PreparedStatement psTech = conn().prepareStatement(sqlTech)) {
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
        String sql = """
            SELECT id,creation_date,expiration_date,athlete_email,pt_email
            FROM training_plan tp
            WHERE tp.athlete_email = ?
            """;
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, athleteEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    //will overwrite current rs,but only one plan in database for an athlete
                    return buildPlan(rs,fetchExercises(rs.getInt("id")));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB searchByAthlete", e);
        }
    }

    @Override
    public List<TrainingPlan> searchByPersonalTrainer(String ptEmail) {
        //to be implemented for another uc
        return List.of();
    }

    @Override
    public void deleteFromStorage(TrainingPlan plan) {

        String sql = "DELETE FROM training_plan WHERE athlete_email = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, plan.getClient());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Errore DB deleteFromStorage training_plan", e);
        }
    }

    // Carica gli esercizi (con tecniche) dato un plan_id
    private List<Exercise> fetchExercises(int planId) throws SQLException {
        String sql = """
            SELECT pe.id, pe.name, pe.target, pe.equipment, pe.sets, pe.reps
            FROM plan_exercise pe
            WHERE pe.plan_id = ?
            """;
        List<Exercise> exercises = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
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
                    ex = applyTechniques(ex, rs.getInt("id"));
                    exercises.add(ex);
                }
            }
        }
        return exercises;
    }

    // Riapplica i decorator nell'ordine in cui sono stati salvati
    private Exercise applyTechniques(Exercise ex, int exerciseId) throws SQLException {
        String sql = "SELECT technique FROM exercise_technique WHERE exercise_id = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, exerciseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ex = switch (rs.getString("technique")) {
                        case "DROP_SET"         -> new DropSetDecorator(ex);
                        case "REST_PAUSE"        -> new RestPauseDecorator(ex);
                        case "SLOW_ECCENTRIC"    -> new SlowEccentricDecorator(ex);
                        case "ISOMETRIC_PAUSE"   -> new IsometricPauseDecorator(ex);
                        case "FORCED_REPS"       -> new ForcedRepsDecorator(ex);
                        default -> ex;
                    };
                }
            }
        }
        return ex;
    }
    private TrainingPlan buildPlan(ResultSet rs, List<Exercise> exercises) throws SQLException {
        return new TrainingPlan(
                rs.getString("athlete_email"),
                rs.getString("pt_email"),
                rs.getDate("creation_date").toLocalDate(),
                rs.getDate("expiration_date").toLocalDate(),
                exercises
        );
    }
}