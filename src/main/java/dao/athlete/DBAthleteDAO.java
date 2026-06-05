package dao.athlete;

import dao.personaltrainer.PersonalTrainerDAO;
import eng.AthleteService;
import eng.DAOFactory;
import eng.DBConnection;
import exception.DAOException;
import model.*;

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
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buildAthlete(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB searchAthleteByEmail", e);
        }
        return null;
    }

    @Override
    public List<Athlete> fetchAthleteByTrainer(String ptEmail) {
        String sql = """
        SELECT email, name, surname, gender, weight, height, pt_email
        FROM athlete
        WHERE pt_email = ?
        """;

        List<Athlete> athletes = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, ptEmail);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Athlete a = buildAthlete(rs);
                    athletes.add(a);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB fetchAthleteByTrainer", e);
        }
        return athletes;
    }

    private Athlete buildAthlete(ResultSet rs) throws SQLException {
        String email   = rs.getString("email");
        String ptEmail = rs.getString("pt_email");

        PersonalTrainer pt   = null;
        if (ptEmail != null) {
            PersonalTrainerDAO ptDAO   = DAOFactory.getInstance().getPersonalTrainerDAO();
            pt   = ptDAO.fetchPtByEmail(ptEmail);
        }
        PhysicalTraits traits=new PhysicalTraits( rs.getDouble("weight"),
                rs.getInt("height"),
                Gender.valueOf(rs.getString("gender")));
        Athlete a=new Athlete(
                email,
                rs.getString("name"),
                rs.getString("surname"),
                traits,
                null,
                null
        );
        return AthleteService.getAthleteWithPlan(a,pt);
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
        } catch (SQLException e) {
            throw new DAOException("Errore DB update athlete", e);
        }
    }

}