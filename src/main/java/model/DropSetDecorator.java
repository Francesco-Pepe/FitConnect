package model;

public class DropSetDecorator extends ExerciseDecorator{
    public DropSetDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
    }
    private String applyDropset(String input){
        return input+" [Technique: Drop Set on the last 2 sets (reduce weight by 30% and perform to failure)]";
    }
    @Override
    public String getExecutionDetails(){
        String oldDetails=super.getExecutionDetails();
        return applyDropset(oldDetails);
    }
}
