package model;

public class SlowEccentricDecorator extends ExerciseDecorator{
    private int seconds;
    private int reps;
    public SlowEccentricDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
        this.seconds=4;
        this.reps=2;
    }
    protected  void setSeconds(int seconds){
        this.seconds=seconds;
    }
    protected void setReps(int reps){
        this.reps=reps;
    }
    protected void increaseSeconds(){
        this.seconds++;
    }
    protected void decreaseSeconds(){
        this.seconds--;
    }
    protected void increaseReps(){
        this.reps++;
    }
    protected void decreaseReps(){
        this.reps--;
    }
    protected String applySlowEccentric(String input){
        return input+ " [Technique: Slow Eccentric ("+seconds+"-second negative phase every "+reps+ "reps)]";
    }
    @Override
    public String getExecutionDetails() {
        String oldDetails=super.getExecutionDetails();
        return  applySlowEccentric(oldDetails);
    }
}
