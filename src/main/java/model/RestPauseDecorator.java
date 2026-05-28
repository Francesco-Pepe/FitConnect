package model;

public class RestPauseDecorator extends ExerciseDecorator{
    private int seconds;
    public RestPauseDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
        this.seconds=15;
    }

    protected void setSeconds(int seconds){
        this.seconds=seconds;
    }
    protected String applyRestPause(String input){
        return input+" [Technique: Rest-Pause (rest "+seconds+" seconds after failure, then perform max additional reps)]";
    }

    @Override
    public String getExecutionDetails() {
        String oldDetails=super.getExecutionDetails();
        return  applyRestPause(oldDetails);
    }
}
