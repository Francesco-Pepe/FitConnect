package dao.authentication;

import exception.DAOException;
import model.Credential;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Implementazione demo dell'AuthenticationDAO.
 * Permette il login con credenziali fisse per testing.
 */
public class DemoAuthenticationDAO extends AuthenticationDAO {

    // Credenziali demo fisse
    private  String athleteEmail ;
    private  String athletePassword ; //BANALE
//demo123

    @Override
    public Credential getAthleteCredential (String email) throws DAOException {
        getDemoCredential();
        if (email.equals(athleteEmail) ) {
            return new Credential(athleteEmail,athletePassword);

        }
        throw new DAOException("Atleta non trovato");
    }
    @Override
    public Credential getPersonalTrainerCredential(String email) throws DAOException {
        if (email.equals(athleteEmail) ){
            // Crea o recupera il trainer di demo
            return new Credential(athleteEmail,athletePassword);
            }

        throw new DAOException("Credenziali demo trainer errate. Usa demo@trainer.com / demo123");
    }

    @Override
    public void registerAthlete(String email, String password) throws DAOException {
        throw new DAOException("Registrazione disabilitata in modalità DEMO");
    }

    @Override
    public void registerPersonalTrainer(String email, String password) throws DAOException {
        throw new DAOException("Registrazione disabilitata in modalità DEMO");
    }
    private void getDemoCredential(){
        try (InputStream in = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(in);

            this.athleteEmail = prop.getProperty("athlete.email");
            this.athletePassword= prop.getProperty("athlete.password");


        } catch (IOException e) {
            throw new DAOException("Errore lettura config.properties", e);
        }
    }
}

