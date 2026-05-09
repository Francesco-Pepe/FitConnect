package eng;

import dao.Athlete.AthleteDAO;
import dao.PersonalTrainer.PersonalTrainerDAO;
import dao.PlanRequest.PlanRequestDAO;
import dao.TrainingPlan.TrainingPlanDAO;
import exception.DAOException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DAOFactory {
    private static DAOFactory instance = null;

    protected DAOFactory() {
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            try (InputStream in = new FileInputStream("config.properties")) {
                Properties prop = new Properties();
                prop.load(in);

                String persistenceType = prop.getProperty("persistence.type", "FILESYSTEM").toUpperCase();

                instance = switch (persistenceType) {
                    case "FILESYSTEM" -> new FileDAOFactory();
                    case "DATABASE" -> new DBDAOFactory();
                    case "DEMO" -> new DemoDAOFactory();
                    default -> new FileDAOFactory(); // Default fallback
                };
            } catch (IOException e) {
                throw new DAOException("Errore lettura config.properties", e);
            }
        }
        return instance;
    }

    // Abstract methods

    public abstract AthleteDAO getAthleteDAO();
    public abstract PersonalTrainerDAO getPersonalTrainerDAO();
    public abstract PlanRequestDAO getPlanRequestDAO();
    public abstract TrainingPlanDAO getTrainingPlanDAO();

    // Static convenience methods (for backward compatibility)
    // These delegate to the current instance's abstract methods

}
