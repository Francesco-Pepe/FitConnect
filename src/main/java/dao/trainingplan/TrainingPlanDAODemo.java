package dao.trainingplan;

import dao.athlete.AthleteDAO;
import eng.DAOFactory;
import model.*;



public class TrainingPlanDAODemo extends TrainingPlanDAO {

    private final AthleteDAO athleteDAO = DAOFactory.getInstance().getAthleteDAO();

    @Override
    public TrainingPlan searchByAthlete(String athleteEmail) {
        Athlete a = athleteDAO.fetchByEmail(athleteEmail);
        return (a != null) ? a.getPlan() : null;
    }

    @Override
    public void save(TrainingPlan plan) {
        //nothing to do we are in ram
    }

}