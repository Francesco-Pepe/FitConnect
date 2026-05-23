package dao.athlete;

import dao.personaltrainer.PersonalTrainerDAO;
import dao.trainingplan.TrainingPlanDAO;
import eng.DAOFactory;
import eng.DBConnection;
import exception.DAOException;
import model.Athlete;
import model.Gender;
import model.PersonalTrainer;
import model.TrainingPlan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBAthleteDAO extends AthleteDAO {

    private Connection conn() {
        return DBConnection.getInstance().getConnection();
    }

    @Override
    public Athlete searchAthleteByEmail(String email) {
        String sql = """
        SELECT email, name, surname, gender, weight, height, pt_email
        FROM athlete
        WHERE email = ?
        """;

        Athlete a = null;
        String ptEmail = null;

        // STEP 1: Recupero l'atleta dal DB
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    a = new Athlete(
                            rs.getString("email"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getDouble("weight"),
                            rs.getInt("height"),
                            Gender.valueOf(rs.getString("gender"))
                    );
                    ptEmail = rs.getString("pt_email");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB searchAthleteByEmail", e);
        }

        // STEP 2: Se l'atleta esiste ED ha un PT, allora (e solo allora) carico PT e Piano
        if (a != null && ptEmail != null) {
            PersonalTrainerDAO ptDAO = DAOFactory.getInstance().getPersonalTrainerDAO();
            TrainingPlanDAO planDAO = DAOFactory.getInstance().getTrainingPlanDAO();

            // Chiamate sequenziali sulla connessione singleton, una dopo l'altra
            PersonalTrainer trainer = ptDAO.getByEmail(ptEmail);
            TrainingPlan plan = planDAO.fetchByAthlete(email);
            a.assignPlan(trainer,plan);
        }

        return a;
    }



    @Override
    public List<Athlete> fetchAthleteByTrainer(String ptEmail) {
        String sql = """
        SELECT email, name, surname, gender, weight, height
        FROM athlete
        WHERE pt_email = ?
        """;

        List<Athlete> athletes = new ArrayList<>();

        // STEP 1: Recupero tutti gli atleti associati al PT
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, ptEmail);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Athlete a = new Athlete(
                            rs.getString("email"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getDouble("weight"),
                            rs.getInt("height"),
                            Gender.valueOf(rs.getString("gender"))
                    );
                    athletes.add(a);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB fetchAthleteByTrainer", e);
        }

        // STEP 2: Se la lista non è vuota, arricchisco gli oggetti
        if (!athletes.isEmpty()) {
            PersonalTrainerDAO ptDAO = DAOFactory.getInstance().getPersonalTrainerDAO();
            TrainingPlanDAO planDAO = DAOFactory.getInstance().getTrainingPlanDAO();

            // Recupero l'istanza del PT una sola volta (valida per tutti gli atleti della lista)
            PersonalTrainer trainer = ptDAO.getByEmail(ptEmail);

            for (Athlete athlete : athletes) {
                // NOTA: Questa chiamata interroga il DB ad ogni ciclo.
                // Finché gli atleti sono pochi gestibile, ma l'ideale futuro
                // sarebbe un metodo tipo planDAO.fetchByAthleteEmails(listaDiEmail)
                athlete.assignPlan(trainer,planDAO.fetchByAthlete(athlete.getEmail()));
            }
        }

        return athletes;
    }

    @Override
    public void update(Athlete a) {
        String sql = """
            UPDATE athlete SET name = ?, surname = ?, gender = ?,
                               weight = ?, height = ?, pt_email = ?
            WHERE email = ?
            """;
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, a.getName());
            ps.setString(2, a.getSurname());
            ps.setString(3, a.getGender().name());
            ps.setDouble(4, a.getWeight());
            ps.setInt(5, a.getHeight());
            ps.setString(6, a.getPt() != null ? a.getPt().getEmail() : null);
            ps.setString(7, a.getEmail());
            ps.executeUpdate();
            // Aggiorna la cache
            addToCache(a);
        } catch (SQLException e) {
            throw new DAOException("Errore DB update athlete", e);
        }
    }

}