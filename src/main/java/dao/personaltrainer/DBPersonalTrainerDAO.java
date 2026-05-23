package dao.personaltrainer;

import eng.DBConnection;
import exception.DAOException;
import model.Gender;
import model.PersonalTrainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBPersonalTrainerDAO extends PersonalTrainerDAO {

    private Connection conn() {
        return DBConnection.getInstance().getConnection();
    }

    @Override
    public PersonalTrainer searchPtByEmail(String email) {
        String sql = "SELECT email, name, surname, gender FROM personal_trainer WHERE email = ?";
        PersonalTrainer pt=null;
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pt=mapRow(rs);
                    addToCache(pt);
                    return pt;
                }
                return pt;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB searchPtByEmail", e);
        }
    }

    @Override
    public List<PersonalTrainer> fetchAll() {
        String sql = "SELECT email, name, surname, gender FROM personal_trainer";
        List<PersonalTrainer> result = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB fetchAll personal trainer", e);
        }
        return result;
    }



    private PersonalTrainer mapRow(ResultSet rs) throws SQLException {
        return new PersonalTrainer(
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("surname"),
                Gender.valueOf(rs.getString("gender"))
        );
    }
}