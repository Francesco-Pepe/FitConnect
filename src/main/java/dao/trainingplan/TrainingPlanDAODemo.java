package dao.trainingplan;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class TrainingPlanDAODemo extends TrainingPlanDAO {

    private final List<TrainingPlan> plans = new ArrayList<>();
    @Override
    public TrainingPlan searchByAthlete(String athleteEmail) {
        return null;
    }

    @Override
    public void save(TrainingPlan plan) {
    }

}