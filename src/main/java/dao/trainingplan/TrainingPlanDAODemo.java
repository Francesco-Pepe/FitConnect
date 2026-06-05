package dao.trainingplan;

import model.*;



public class TrainingPlanDAODemo extends TrainingPlanDAO {

    @Override
    public TrainingPlan searchByAthlete(String athleteEmail) {
        return null;
    }

    @Override
    public void save(TrainingPlan plan) {
        //nothing to do we are in ram
    }

}