package dao.authentication;

import exception.DAOException;
import model.Credential;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DemoAuthenticationDAO extends AuthenticationDAO {
    private  String athleteEmail ;
    private  String athletePassword;
    private  String ptEmail;
    private String ptPassword;
    public DemoAuthenticationDAO(){
        try (InputStream in = new FileInputStream("config.properties")) {
        Properties prop = new Properties();
        prop.load(in);
        //same password for both
        this.athleteEmail = prop.getProperty("demo.athlete");
        this.athletePassword= prop.getProperty("demo.password");
        this.ptEmail= prop.getProperty("demo.trainer");
        this.ptPassword=prop.getProperty("demo.password");
        }
        catch (IOException e)
        {
        throw new DAOException("Error reading config.properties", e);
        }

    }

    @Override
    public Credential getAthleteCredential (String email) throws DAOException {
        if (email.equals(athleteEmail) ) {
            return new Credential(athleteEmail,athletePassword);

        }
        throw new DAOException("Athlete not found");
    }
    @Override
    public Credential getPersonalTrainerCredential(String email) throws DAOException {
        if (email.equals(ptEmail) ){
            // Crea o recupera il trainer di demo
            return new Credential(ptEmail,ptPassword);
            }

        throw new DAOException("Credentials not found.");
    }

    @Override
    public void registerAthlete(String email, String password) throws DAOException {
        throw new DAOException("Registrazione disabilitata in modalità DEMO");
    }

    @Override
    public void registerPersonalTrainer(String email, String password) throws DAOException {
        throw new DAOException("Registrazione disabilitata in modalità DEMO");
    }

}

