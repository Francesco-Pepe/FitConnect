package api;

import java.io.IOException;

public interface ExerciseApiService {
    ExternalApiExerciseDTO fetchExerciseByName(String exerciseName) throws IOException, InterruptedException;
}
