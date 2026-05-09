package eng;

import dao.Athlete.AthleteDAO;
import dao.PersonalTrainer.PersonalTrainerDAO;
import dao.PlanRequest.DBPlanRequestDAO;
import dao.PlanRequest.PlanRequestDAO;
import dao.TrainingPlan.DBTrainingPlanDAO;
import dao.TrainingPlan.TrainingPlanDAO;
import dao.PersonalTrainer.DBPersonalTrainerDAO;
import dao.Athlete.DBAthleteDAO;
/**
 * DBDAOFactory è la factory concreta per la persistenza su database.
 * Attualmente è un placeholder - le implementazioni DB DAO devono ancora essere create.
 */
public class DBDAOFactory extends DAOFactory {

    private AthleteDAO athleteDAO=null;
    private  PersonalTrainerDAO personalTrainerDAO=null;
    private  PlanRequestDAO planRequestDAO=null;
    private  TrainingPlanDAO trainingPlanDAO=null;

    public DBDAOFactory() {
        super();
    }

    @Override
    public synchronized AthleteDAO getAthleteDAO() {
        if (athleteDAO==null){
            this.athleteDAO=new DBAthleteDAO();
        }
        return athleteDAO;
    }

    @Override
    public synchronized PersonalTrainerDAO getPersonalTrainerDAO() {
        if(personalTrainerDAO==null){
            this.personalTrainerDAO=new DBPersonalTrainerDAO();
        }
        return this.personalTrainerDAO;
    }

    @Override
    public synchronized PlanRequestDAO getPlanRequestDAO() {
        if (personalTrainerDAO==null){
            this.planRequestDAO=new DBPlanRequestDAO();
        }
        return this.planRequestDAO;
    }

    @Override
    public synchronized TrainingPlanDAO getTrainingPlanDAO() {
        if (trainingPlanDAO==null){
            this.trainingPlanDAO=new DBTrainingPlanDAO();
        }
        return trainingPlanDAO;
    }
}

