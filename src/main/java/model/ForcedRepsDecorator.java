package model;

public class ForcedRepsDecorator extends ExerciseDecorator{

    public ForcedRepsDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
    }
    private String applyForcedReps(String input){
        return input +" [Technique: Forced Reps (spotter assistance for 1-2 extra reps after failure)]";
    }
    @Override
    public String getExecutionDetails() {
        String oldDetails=super.getExecutionDetails();
        return  applyForcedReps(oldDetails);
    }
}
