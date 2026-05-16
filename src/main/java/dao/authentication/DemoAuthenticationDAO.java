package dao.authentication;
import exception.DAOException;
import model.Credential;


/**
 * Implementazione demo dell'AuthenticationDAO.
 * Permette il login con credenziali fisse per testing.
 */
public class DemoAuthenticationDAO extends AuthenticationDAO {

    // Credenziali demo fisse
    private static final String DEMO_ATHLETE_EMAIL = "demo@athlete.com";
    private static final String DEMO_ATHLETE_PASSWORD = "CBOBMF";//BANALE

    private static final String DEMO_TRAINER_EMAIL = "demo@trainer.com";
    private static final String DEMO_TRAINER_PASSWORD = "efnp123"; //demo123

    @Override
    public Credential getAthleteCredential (String email) throws DAOException {
        if (email.equals(DEMO_ATHLETE_EMAIL) ) {
            return new Credential(DEMO_ATHLETE_EMAIL,DEMO_ATHLETE_PASSWORD);

        }
        throw new DAOException("Atleta non trovato");
    }
    @Override
    public Credential getPersonalTrainerCredential(String email) throws DAOException {
        if (email.equals(DEMO_TRAINER_EMAIL) ){
            // Crea o recupera il trainer di demo
            return new Credential(DEMO_TRAINER_EMAIL,DEMO_TRAINER_PASSWORD);
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
}

