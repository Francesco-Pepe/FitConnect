package dao.trainingplan;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class TrainingPlanDAODemo extends TrainingPlanDAO {

    private final List<TrainingPlan> plans = new ArrayList<>();
    public TrainingPlanDAODemo() {
    }


    @Override
    public TrainingPlan searchByAthlete(String athleteEmail) {
        return null;
    }

    @Override
    public List<TrainingPlan> searchByPersonalTrainer(String ptEmail) {
        //not used,to be implemented for another uc
        return null;
    }
    @Override
    public void save(TrainingPlan plan) {
        addToCache(plan);
    }

    @Override
    public void deleteFromStorage(TrainingPlan plan) {
        //not used yet
        plans.removeIf(p -> p.getClient().equals(plan.getClient()));
    }
}