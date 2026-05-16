package bean;


import java.time.LocalDate;
import java.util.List;

public class TrainingPlanBean {

    private LocalDate creation;
    private LocalDate expiration;
    private List<ExerciseBean> exercises;


    public TrainingPlanBean(LocalDate creation,LocalDate expiration,List<ExerciseBean> exercises){
        this.expiration=expiration;
        this.exercises=exercises;
        this.creation=creation;
    }


    public LocalDate getCreation(){
        return this.creation;
    }
    public LocalDate getExpiration(){
        return this.expiration;
    }

    public List<ExerciseBean> getExercises(){
        return this.exercises;
    }


}
