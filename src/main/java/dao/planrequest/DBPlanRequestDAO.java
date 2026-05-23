package dao.planrequest;

import eng.DBConnection;
import exception.DAOException;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBPlanRequestDAO extends PlanRequestDAO {

    private Connection conn() {
        return DBConnection.getInstance().getConnection();
    }

    @Override
    protected PlanRequest searchRequestById(int id) {
        String sql = """
            SELECT id,goal,status,athlete_email,pt_email
            FROM plan_request pr
            WHERE pr.id = ?
            """;
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {return new PlanRequest(
                        rs.getInt("id"),
                        rs.getString("athlete_email"), rs.getString("pt_email"),
                        FitnessGoal.valueOf(rs.getString("goal")),
                        RequestStatus.valueOf(rs.getString("status")
                                ));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB searchRequestById", e);
        }
    }

    @Override
    public void save(PlanRequest request) {
        String sql = """
            INSERT INTO plan_request (athlete_email, pt_email, goal, status)
            VALUES (?, ?, ?, ?)
            """;
        try (PreparedStatement ps = conn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, request.getClientEmail());
            ps.setString(2, request.getPtEmail());
            ps.setString(3, request.getGoal().name());
            ps.setString(4, request.getStatus().name());
            ps.executeUpdate();
            // Recupera l'id generato dal DB e lo assegna all'oggetto
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    request.setId(keys.getInt(1));
                    addToCache(request);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB save plan_request", e);
        }
    }

    @Override
    public void update(PlanRequest request) {
        String sql = "UPDATE plan_request SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, request.getStatus().name());
            ps.setInt(2, request.getId());
            ps.executeUpdate();
            addToCache(request);
        } catch (SQLException e) {
            throw new DAOException("Errore DB update plan_request", e);
        }
    }

    @Override
    public List<PlanRequest> fetchByAthlete(String athleteEmail) {
        String sql = """
            SELECT id, goal, status,athlete_email,pt_email
            FROM plan_request pr
            WHERE pr.athlete_email = ?
            """;
        return fetchList(sql, athleteEmail);
    }

    @Override
    public List<PlanRequest> fetchPendingByTrainer(String trainerEmail) {
        String sql = """
            SELECT id,goal,status,pt_email,athlete_email
            FROM plan_request pr
            WHERE pt_email = ? AND pr.status = 'PENDING'
            """;
        return fetchList(sql, trainerEmail);
    }

    // helper condiviso per le query che ritornano una lista
    private List<PlanRequest> fetchList(String sql, String param) {
        List<PlanRequest> result = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB fetchList plan_request", e);
        }
        return result;
    }

    private PlanRequest mapRow(ResultSet rs) throws SQLException {
        return new PlanRequest(
                rs.getInt("id"),
                rs.getString("athlete_email"),
                rs.getString("pt_email"),
                FitnessGoal.valueOf(rs.getString("goal")),
                RequestStatus.valueOf(rs.getString("status"))
        );
    }
}