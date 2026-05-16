package eng;

import dao.athlete.AthleteDAO;
import dao.athlete.FileAthleteDAO;
import dao.authentication.AuthenticationDAO;
import dao.authentication.FileAuthenticationDAO;
import dao.personaltrainer.FilePersonalTrainerDAO;
import dao.personaltrainer.PersonalTrainerDAO;
import dao.planrequest.FilePlanRequestDAO;
import dao.planrequest.PlanRequestDAO;
import dao.trainingplan.FileTrainingPlanDAO;
import dao.trainingplan.TrainingPlanDAO;

public class FileDAOFactory extends DAOFactory {
    private AthleteDAO athleteDAO=null;
    private  PersonalTrainerDAO personalTrainerDAO=null;
    private  PlanRequestDAO planRequestDAO=null;
    private  TrainingPlanDAO trainingPlanDAO=null;
    private AuthenticationDAO authenticationDAO=null;

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
        if (planRequestDAO==null){
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

    @Override
    public synchronized AuthenticationDAO getAuthenticationDAO() {
        if (authenticationDAO==null) {
            this.authenticationDAO = new FileAuthenticationDAO();
        }
        return authenticationDAO;
    }
}

