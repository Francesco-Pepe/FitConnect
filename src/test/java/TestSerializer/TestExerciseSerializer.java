package TestSerializer;

import eng.ExerciseSerializer;
import model.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 class TestExerciseSerializer {

    private BaseExercise baseExercise() {
        return new BaseExercise("bench press", 12, 4, "barbell", "chest");
    }

    @Test
    void testSerializeBase() {
        JSONObject json = ExerciseSerializer.serialize(baseExercise());

        assertEquals("bench press", json.getString("name"));
        assertEquals("chest", json.getString("target"));
        assertEquals("barbell", json.getString("equipment"));
        assertEquals(4, json.getInt("sets"));
        assertEquals(12, json.getInt("reps"));
        assertEquals(0, json.getJSONArray("techniques").length());
    }
}
