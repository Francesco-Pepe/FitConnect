package model;

public class SlowEccentricDecorator extends ExerciseDecorator{
    public SlowEccentricDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
    }
    private String applySlowEccentric(String input){
        return input+ " [Technique: Slow Eccentric (3-second negative phase every 2 reps)]";
    }
    @Override
    public String getExecutionDetails() {
        String oldDetails=super.getExecutionDetails();
        return  applySlowEccentric(oldDetails);
    }
}
