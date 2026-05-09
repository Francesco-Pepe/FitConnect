package dao.planrequest;

import exception.DAOException;
import model.*;

import java.util.List;

public class PlanRequestDAODemo extends PlanRequestDAO{
    @Override
    protected PlanRequest searchRequestById(int id) {
        PlanRequest req=null;
        if (id<0){
            throw new DAOException("L'id deve essere >0");
        }
        if (id==1) {
            Athlete a = new Athlete("frank@gmail.com", "Francesco", "Pepe", 78, 178, Gender.MALE);
            PersonalTrainer pt = new PersonalTrainer("trainer@fit.com", "Mario", "Rossi", Gender.MALE);
            req = new PlanRequest(id, a, pt, FitnessGoal.STRENGHT);
        }
        return req;
    }

    @Override
    public void save(PlanRequest request) {
    //nothing to do
    }

    @Override
    public void update(PlanRequest request) {
    //nothing to do
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
