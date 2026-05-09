package eng;

import dao.athlete.AthleteDAO;
import dao.athlete.AthleteDAODemo;
import dao.personaltrainer.PersonalTrainerDAO;
import dao.personaltrainer.PersonalTrainerDAODemo;
import dao.planrequest.PlanRequestDAO;
import dao.planrequest.PlanRequestDAODemo;
import dao.trainingplan.TrainingPlanDAO;
import dao.trainingplan.TrainingPlanDAODemo;

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

