package dao;

import model.PlanRequest;

import java.util.List;

public interface PlanRequestDAO {
    void save(PlanRequest request);
    void update(PlanRequest request);
    void delete(PlanRequest request);
    List<PlanRequest> findAll();
}
