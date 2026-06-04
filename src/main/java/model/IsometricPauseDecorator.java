package model;

public class IsometricPauseDecorator extends ExerciseDecorator{
    public IsometricPauseDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
    }
    private String applyIsometricPause(String input){
        return input+ " [Technique: Isometric Pause (2-second hold at peak contraction)]";
    }
    @Override
    public String getExecutionDetails() {
        String oldDetails=super.getExecutionDetails();
        return applyIsometricPause(oldDetails);
    }
}
