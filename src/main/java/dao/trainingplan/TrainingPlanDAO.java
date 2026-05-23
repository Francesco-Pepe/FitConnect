package dao.trainingplan;

import eng.CachedDAO;
import model.TrainingPlan;

import java.util.List;

public abstract class TrainingPlanDAO extends CachedDAO<TrainingPlan> {
    @Override
    public String fetchKey(TrainingPlan plan){
        return plan.getClient();
    }

    public  void delete(TrainingPlan plan){
        deleteFromCache(plan);
        deleteFromStorage(plan);
    }
    public abstract void deleteFromStorage(TrainingPlan plan);

    public TrainingPlan fetchByAthlete(String email){
        TrainingPlan p;
        if (inCache(email)){
            p=fetchFromCache(email);
        }
        else{
            p=searchByAthlete(email);
            if (p!=null){
                addToCache(p);
            }
        }
        return p;
    }

    public abstract TrainingPlan searchByAthlete(String atEmail);
    public  List<TrainingPlan> fetchByPersonalTrainer(String ptEmail){
        List<TrainingPlan> plans=searchByPersonalTrainer(ptEmail);
        plans.forEach(this::addToCache);
        return plans;
    }

    public  abstract void save(TrainingPlan plan);
    public abstract List<TrainingPlan> searchByPersonalTrainer(String ptEmail);

}
