package model;

public class RestPauseDecorator extends ExerciseDecorator{
    public RestPauseDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
    }
    private String applyRestPause(String input){
        return input+" [Technique: Rest-Pause (rest 15 seconds after failure, then perform max additional reps)]";
    }

    @Override
    public String getExecutionDetails() {
        String oldDetails=super.getExecutionDetails();
        return  applyRestPause(oldDetails);
    }
}
