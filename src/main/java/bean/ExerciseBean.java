package bean;

import java.util.List;

public class ExerciseBean {
    private String name;
    private int sets;
    private int reps;
    private String executionDetails;
    private List<Technique> tecniques;

    public  ExerciseBean(String name,int sets,int reps,String executionDetails){
        this.name=name;
        this.sets=sets;
        this.reps=reps;
        this.executionDetails=executionDetails;
    }

    //costruttore per bean creato dal pt durante la creazione del piano
    public ExerciseBean(String name,int sets,int reps,List<Technique> techniques){
        this.name=name;
        this.sets=sets;
        this.reps=reps;
        this.tecniques=techniques;
    }

    public String getExerciseName(){
        return this.name;
    }

    public int getSets(){
        return this.sets;
    }

    public int getReps(){
        return this.reps;
    }

    public String getExecutionDetails(){
        return this.executionDetails;
    }

    public List<Technique> getTechniques() {
        return tecniques;
    }

    public void setTechniques(List<Technique> tecniques) {
        this.tecniques = tecniques;
    }
}
