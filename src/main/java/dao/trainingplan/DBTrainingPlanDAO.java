package dao.trainingplan;

import model.TrainingPlan;

import java.util.List;

public class DBTrainingPlanDAO extends TrainingPlanDAO {
    @Override
    public void deleteFromStorage(TrainingPlan plan) {
        //to implement
    }

    @Override
    public TrainingPlan searchByAthlete(String atEmail) {
        return null;
    }

    @Override
    public void save(TrainingPlan plan) {
        // to implement
    }

    @Override
    public List<TrainingPlan> searchByPersonalTrainer(String ptEmail) {
        return List.of();
    }

}
