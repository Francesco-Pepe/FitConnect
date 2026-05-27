package TestMapper;

import api.ExternalApiExerciseDTO;
import eng.ExerciseMapper;
import model.BaseExercise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class TestExerciseMapper {
    @Test
    void testMapping() {
        ExternalApiExerciseDTO dto = new ExternalApiExerciseDTO("barbell", "chest", "bench press");
        BaseExercise result = ExerciseMapper.fromDTO(dto, 4, 12);

        assertEquals("bench press", result.getName());
        assertEquals("barbell", result.getEquipment());
        assertEquals("chest", result.getTarget());
        assertEquals(4, result.getSets());
        assertEquals(12, result.getReps());
    }
}