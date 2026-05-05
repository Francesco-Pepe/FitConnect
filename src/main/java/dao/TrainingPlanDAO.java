package dao;

import model.TrainingPlan;

import java.util.List;

public interface TrainingPlanDAO {
    void save(TrainingPlan plan);
    void update(TrainingPlan plan);
    List<TrainingPlan> findAll();
}
