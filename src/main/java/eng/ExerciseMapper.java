package eng;

import api.ExternalApiExerciseDTO;
import model.BaseExercise;

public class ExerciseMapper {
    private ExerciseMapper(){}

    public static BaseExercise fromDTO(ExternalApiExerciseDTO dto, int sets, int reps) {
        return new BaseExercise(
                dto.getName(),
                reps,
                sets,
                dto.getEquipment(),
                dto.getTarget()
        );
    }
}

