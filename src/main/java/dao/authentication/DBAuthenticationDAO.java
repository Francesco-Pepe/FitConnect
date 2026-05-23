package dao.authentication;


import eng.DBConnection;
import eng.PasswordEncoder;
import exception.DAOException;
import model.Credential;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAuthenticationDAO extends AuthenticationDAO {

    private Connection conn() {
        return DBConnection.getInstance().getConnection();
    }

    @Override
    public Credential getAthleteCredential(String email) throws DAOException {
        String sql = "SELECT email, hash_password FROM athlete_credentials WHERE email = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Credential(rs.getString("email"), rs.getString("hash_password"));
                }
                throw new DAOException("Credenziali atleta non trovate per: " + email);
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB getAthleteCredential", e);
        }
    }

    @Override
    public Credential getPersonalTrainerCredential(String email) throws DAOException {
        String sql = "SELECT email, hash_password FROM trainer_credentials WHERE email = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Credential(rs.getString("email"), rs.getString("hash_password"));
                }
                throw new DAOException("Credenziali trainer non trovate per: " + email);
            }
        } catch (SQLException e) {
            throw new DAOException("Errore DB getPersonalTrainerCredential", e);
        }
    }

    @Override
    public void registerAthlete(String email, String password) throws DAOException {
        String sql = "INSERT INTO athlete_credentials (email, hash_password) VALUES (?, ?)";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, PasswordEncoder.hashPassword(password));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Errore DB registerAthlete", e);
        }
    }

    @Override
    public void registerPersonalTrainer(String email, String password) throws DAOException {
        String sql = "INSERT INTO trainer_credentials (email, hash_password) VALUES (?, ?)";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, PasswordEncoder.hashPassword(password));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Errore DB registerPersonalTrainer", e);
        }
    }
}
