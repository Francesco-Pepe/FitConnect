package eng;

import dao.Athlete.AthleteDAO;
import dao.Athlete.AthleteDAODemo;
import dao.Athlete.FileAthleteDAO;
import dao.PersonalTrainer.FilePersonalTrainerDAO;
import dao.PersonalTrainer.PersonalTrainerDAO;
import dao.PersonalTrainer.PersonalTrainerDAODemo;
import dao.PlanRequest.FilePlanRequestDAO;
import dao.PlanRequest.PlanRequestDAO;
import dao.PlanRequest.PlanRequestDAODemo;
import dao.TrainingPlan.FileTrainingPlanDAO;
import dao.TrainingPlan.TrainingPlanDAO;
import dao.TrainingPlan.TrainingPlanDAODemo;

public class DemoDAOFactory extends DAOFactory {
    private AthleteDAO athleteDAO=null;
    private  PersonalTrainerDAO personalTrainerDAO=null;
    private  PlanRequestDAO planRequestDAO=null;
    private  TrainingPlanDAO trainingPlanDAO=null;

    public DemoDAOFactory() {
        super();
    }

    @Override
    public synchronized AthleteDAO getAthleteDAO() {
        if (athleteDAO==null){
            this.athleteDAO=new AthleteDAODemo();
        }
        return athleteDAO;
    }

    @Override
    public synchronized PersonalTrainerDAO getPersonalTrainerDAO() {
        if(personalTrainerDAO==null){
            this.personalTrainerDAO=new PersonalTrainerDAODemo();
        }
        return this.personalTrainerDAO;
    }

    @Override
    public synchronized PlanRequestDAO getPlanRequestDAO() {
        if (personalTrainerDAO==null){
            this.planRequestDAO=new PlanRequestDAODemo();
        }
        return this.planRequestDAO;
    }

    @Override
    public synchronized TrainingPlanDAO getTrainingPlanDAO() {
        if (trainingPlanDAO==null){
            this.trainingPlanDAO=new TrainingPlanDAODemo();
        }
        return trainingPlanDAO;
    }
}

