package dao.trainingplan;

import model.TrainingPlan;


public abstract class TrainingPlanDAO  {

    public String fetchKey(TrainingPlan plan){
        return plan.getAthlete().getEmail();
    }

    public abstract TrainingPlan searchByAthlete(String atEmail);
    public  abstract void save(TrainingPlan plan);

}
