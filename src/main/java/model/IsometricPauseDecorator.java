package model;

public class IsometricPauseDecorator extends ExerciseDecorator{
    private int isometricHold;
    public IsometricPauseDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
        this.isometricHold=2;
    }
    protected void setPause(int pause){
        this.isometricHold=pause;
    }
    protected String applyIsometricPause(String input){
        return input+ " [Technique: Isometric Pause ("+isometricHold+"-second hold at peak contraction)]";
    }
    @Override
    public String getExecutionDetails() {
        String oldDetails=super.getExecutionDetails();
        return applyIsometricPause(oldDetails);
    }
}
