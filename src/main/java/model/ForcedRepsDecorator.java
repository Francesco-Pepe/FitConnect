package model;

public class ForcedRepsDecorator extends ExerciseDecorator{
    public ForcedRepsDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
    }
    @Override
    public String getExecutionDetails() {
        return this.wrapperExercise.getExecutionDetails() +
                " -> [Technique: Forced Reps (spotter assistance for 1-2 extra reps after failure)]";
    }
}
