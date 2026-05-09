package eng;

import dao.Athlete.AthleteDAO;
import dao.Athlete.FileAthleteDAO;
import dao.PersonalTrainer.FilePersonalTrainerDAO;
import dao.PersonalTrainer.PersonalTrainerDAO;
import dao.PlanRequest.FilePlanRequestDAO;
import dao.PlanRequest.PlanRequestDAO;
import dao.TrainingPlan.FileTrainingPlanDAO;
import dao.TrainingPlan.TrainingPlanDAO;
import model.PlanRequest;

public class FileDAOFactory extends DAOFactory {
    private AthleteDAO athleteDAO=null;
    private  PersonalTrainerDAO personalTrainerDAO=null;
    private  PlanRequestDAO planRequestDAO=null;
    private  TrainingPlanDAO trainingPlanDAO=null;

    public FileDAOFactory() {
        super();
    }

    @Override
    public synchronized AthleteDAO getAthleteDAO() {
        if (athleteDAO==null){
            this.athleteDAO=new FileAthleteDAO();
        }
        return athleteDAO;
    }

    @Override
    public synchronized PersonalTrainerDAO getPersonalTrainerDAO() {
        if(personalTrainerDAO==null){
            this.personalTrainerDAO=new FilePersonalTrainerDAO();
        }
        return this.personalTrainerDAO;
    }

    @Override
    public synchronized PlanRequestDAO getPlanRequestDAO() {
        if (personalTrainerDAO==null){
            this.planRequestDAO=new FilePlanRequestDAO();
        }
        return this.planRequestDAO;
    }

    @Override
    public synchronized TrainingPlanDAO getTrainingPlanDAO() {
        if (trainingPlanDAO==null){
            this.trainingPlanDAO=new FileTrainingPlanDAO();
        }
        return trainingPlanDAO;
    }
}

