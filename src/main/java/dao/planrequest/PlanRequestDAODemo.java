package dao.planrequest;

import exception.DAOException;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class PlanRequestDAODemo extends PlanRequestDAO{
    private static int id_counter=1;
    @Override
    protected PlanRequest searchRequestById(int id) {
        PlanRequest req=null;
        if (id<0){
            throw new DAOException("L'id deve essere >0");
        }
        if (id==1) {
            Athlete a = new Athlete("frank@gmail.com", "Francesco", "Pepe", 78, 178, Gender.MALE);
            PersonalTrainer pt = new PersonalTrainer("trainer@fit.com", "Mario", "Rossi", Gender.MALE);
            req = new PlanRequest(id, a.getEmail(), pt.getEmail(), FitnessGoal.STRENGHT);
        }
        return req;
    }

    @Override
    public void save(PlanRequest request) {
    addToCache(request);
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
        List<PlanRequest> reqs=new ArrayList<>();
        reqs.add(fetchFromCache("1"));
        return reqs;
    }
    @Override
    public int getMaxId(){
        return id_counter++;
    }
}
