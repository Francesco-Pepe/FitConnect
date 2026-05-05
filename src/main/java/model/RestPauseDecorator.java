package model;

public class RestPauseDecorator extends ExerciseDecorator{
    public RestPauseDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
    }

    @Override
    public String getExecutionDetails() {
        return this.wrapperExercise.getExecutionDetails() +
                " -> [Technique: Rest-Pause (rest 15 seconds after failure, then perform max additional reps)]";
    }
}
