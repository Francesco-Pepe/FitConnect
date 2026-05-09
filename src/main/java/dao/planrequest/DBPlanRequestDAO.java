package dao.planrequest;

import model.PlanRequest;

import java.util.List;

public class DBPlanRequestDAO extends PlanRequestDAO{
    @Override
    protected PlanRequest searchRequestById(int id) {
        return null;
    }

    @Override
    public void save(PlanRequest request) {
        //to implement
    }

    @Override
    public void update(PlanRequest request) {
        //to implement
    }

    @Override
    public List<PlanRequest> fetchByAthlete(String athleteEmail) {
        return List.of();
    }

    @Override
    public List<PlanRequest> fetchPendingByTrainer(String trainerEmail) {
        return List.of();
    }
}
