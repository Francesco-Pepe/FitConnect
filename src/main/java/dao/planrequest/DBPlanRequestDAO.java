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
    public PlanRequest searchRequestById(int id) {
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
            ps.setString(1, request.getClientEmail());
            ps.setString(2, request.getPtEmail());
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

    private List<PlanRequest> fetchList(String sql, String param) {
        List<PlanRequest> result = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(buildRequest(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error DB fetchList plan_request", e);
        }
        return result;
    }

    private PlanRequest buildRequest(ResultSet rs) throws SQLException {
        return new PlanRequest(
                rs.getInt("id"),
                rs.getString("athlete_email"),
                rs.getString("pt_email"),
                FitnessGoal.valueOf(rs.getString("goal")),
                RequestStatus.valueOf(rs.getString("status"))
        );
    }

    @Override
    public int getMaxId() {
        try (Connection conn = DBConnection.getInstance().getConnection();
                 Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT MAX(id) FROM plan_request")) {
                    return rs.next() ? rs.getInt(1) + 1 : 1;
        } catch (SQLException e) {
            throw new DAOException("Error retrieving ID", e);
            }
        }
    }
