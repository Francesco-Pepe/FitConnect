package dao.TrainingPlan;

import model.TrainingPlan;

import java.util.List;

public class DBTrainingPlanDAO extends TrainingPlanDAO {
    @Override
    public void deleteFromStorage(TrainingPlan plan) {

    }

    @Override
    public TrainingPlan searchByAthlete(String at_email) {
        return null;
    }

    @Override
    public List<TrainingPlan> searchByPersonalTrainer(String ptEmail) {
        return List.of();
    }
}
