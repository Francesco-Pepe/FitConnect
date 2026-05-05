package model;

public class SlowEccentricDecorator extends ExerciseDecorator{
    public SlowEccentricDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
    }
    @Override
    public String getExecutionDetails() {
        return this.wrapperExercise.getExecutionDetails() +
                " -> [Technique: Slow Eccentric (4-second negative phase on every rep)]";
    }
}
