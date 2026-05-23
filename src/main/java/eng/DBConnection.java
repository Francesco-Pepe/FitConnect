package eng;

import exception.DAOException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static DBConnection instance = null;
    private Connection connection;

    private DBConnection() {
        try (InputStream in = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(in);

            String url      = prop.getProperty("db.url");
            String user     = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            this.connection = DriverManager.getConnection(url, user, password);

        } catch (IOException | SQLException e) {
            throw new DAOException("Impossibile connettersi al database", e);
        }
    }

    public static synchronized DBConnection getInstance() {
        try {
            if (instance == null || instance.connection.isClosed()) {
                instance = new DBConnection();
            }
        } catch (SQLException e) {
            throw new DAOException("Errore verifica connessione", e);
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }
    public void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }
    public void rollback() {
        try { connection.rollback(); connection.setAutoCommit(true); }
        catch (SQLException ignored) {}
    }
}
