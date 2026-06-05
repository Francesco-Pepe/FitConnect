package eng;

import dao.trainingplan.TrainingPlanDAO;
import model.Athlete;
import model.PersonalTrainer;
import model.TrainingPlan;

public class AthleteService {
    private static TrainingPlanDAO daoPlan=DAOFactory.getInstance().getTrainingPlanDAO();

    public static Athlete getAthleteWithPlan(Athlete a, PersonalTrainer pt){
        TrainingPlan plan=daoPlan.searchByAthlete(a.getEmail());
        if (plan!=null){
            a.assignPlan(pt,plan);
            plan.setAthlete(a);
        }
        return a;
    }
}
