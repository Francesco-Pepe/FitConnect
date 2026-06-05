package dao.planrequest;

import dao.athlete.AthleteDAO;
import dao.personaltrainer.PersonalTrainerDAO;
import eng.DAOFactory;
import eng.DBConnection;
import exception.DAOException;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBPlanRequestDAO extends PlanRequestDAO {

    private record RawRequest(int id, String goal, String status,
                              String athleteEmail, String ptEmail) {}

    private Connection conn() {
        return DBConnection.getInstance().getConnection();
    }

    @Override
    public PlanRequest searchRequestById(int id) {
        String sql = """
            SELECT id, goal, status, athlete_email, pt_email
            FROM plan_request pr
            WHERE pr.id = ?
            """;
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buildRequest(new RawRequest(
                            rs.getInt("id"),
                            rs.getString("goal"),
                            rs.getString("status"),
                            rs.getString("athlete_email"),
                            rs.getString("pt_email")
                    ));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error DB searchRequestById", e);
        }
    }

    @Override
    public void save(PlanRequest request) {
        String sql = """
            INSERT INTO plan_request (athlete_email, pt_email, goal, status)
            VALUES (?, ?, ?, ?)
            """;
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, request.getAthlete().getEmail());
            ps.setString(2, request.getPt().getEmail());
            ps.setString(3, request.getGoal().name());
            ps.setString(4, request.getStatus().name());
            ps.executeUpdate();
            addToCache(request);
        } catch (SQLException e) {
            throw new DAOException("Error DB save plan_request", e);
        }
    }

    @Override
    public void update(PlanRequest request) {
        String sql = "UPDATE plan_request SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, request.getStatus().name());
            ps.setInt(2, request.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error DB update plan_request", e);
        }
    }

    @Override
    public List<PlanRequest> fetchByAthlete(String athleteEmail) {
        String sql = """
            SELECT id, goal, status, athlete_email, pt_email
            FROM plan_request pr
            WHERE pr.athlete_email = ?
            """;
        return fetchList(sql, athleteEmail);
    }

    @Override
    public List<PlanRequest> fetchPendingByTrainer(String trainerEmail) {
        String sql = """
            SELECT id, goal, status, athlete_email, pt_email
            FROM plan_request pr
            WHERE pt_email = ? AND pr.status = 'PENDING'
            """;
        return fetchList(sql, trainerEmail);
    }

    private List<PlanRequest> fetchList(String sql, String param) {
        List<RawRequest> rows = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new RawRequest(
                            rs.getInt("id"),
                            rs.getString("goal"),
                            rs.getString("status"),
                            rs.getString("athlete_email"),
                            rs.getString("pt_email")
                    ));
                }
            } // ResultSet chiuso — la connessione è libera per i sub-DAO
        } catch (SQLException e) {
            throw new DAOException("Error DB fetchList plan_request", e);
        }
        List<PlanRequest> result = new ArrayList<>();
        for (RawRequest row : rows)
            result.add(buildRequest(row));
        return result;
    }

    private PlanRequest buildRequest(RawRequest r) {
        PersonalTrainerDAO ptDAO  = DAOFactory.getInstance().getPersonalTrainerDAO();
        AthleteDAO athleteDAO     = DAOFactory.getInstance().getAthleteDAO();
        PersonalTrainer pt        = ptDAO.fetchPtByEmail(r.ptEmail());
        Athlete a                 = athleteDAO.fetchByEmail(r.athleteEmail());
        return new PlanRequest(r.id(), RequestStatus.valueOf(r.status()),
                FitnessGoal.valueOf(r.goal()),
                pt,
                a);
    }

    @Override
    public int getMaxId() {
        String sql = "SELECT MAX(id) FROM plan_request";
        try (PreparedStatement ps = conn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) + 1 : 1;
        } catch (SQLException e) {
            throw new DAOException("Error retrieving ID", e);
        }
    }
}