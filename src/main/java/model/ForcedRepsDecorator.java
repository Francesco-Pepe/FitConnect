package model;

public class ForcedRepsDecorator extends ExerciseDecorator{
    private int minReps;
    private int maxReps;
    public ForcedRepsDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
        this.minReps=1;
        this.maxReps=2;
    }
    protected void setMinReps(int reps){
        this.minReps=reps;
    }
    protected void setMaxReps(int reps){
        this.maxReps=reps;
    }
    protected String applyForcedReps(String input){
        return input +" [Technique: Forced Reps (spotter assistance for "+minReps+"-"+maxReps +"extra reps after failure)]";
    }
    @Override
    public String getExecutionDetails() {
        String oldDetails=super.getExecutionDetails();
        return  applyForcedReps(oldDetails);
    }
}
