package model;

public class IsometricPauseDecorator extends ExerciseDecorator{
    public IsometricPauseDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
    }
    @Override
    public String getExecutionDetails() {
        return this.wrapperExercise.getExecutionDetails() +
                " -> [Technique: Isometric Pause (2-second hold at peak contraction)]";
    }
}
