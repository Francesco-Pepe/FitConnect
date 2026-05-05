package model;

public class DropSetDecorator extends ExerciseDecorator{
    public DropSetDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
    }
    @Override
    public String getExecutionDetails(){
        return this.wrapperExercise.getExecutionDetails()+" -> [Technique: Drop Set on the last set (reduce weight by 20% and perform to failure)]";
    }
}
