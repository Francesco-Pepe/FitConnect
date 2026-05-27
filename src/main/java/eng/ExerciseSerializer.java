package eng;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExerciseSerializer {

    private ExerciseSerializer(){}
    public static JSONObject serialize(Exercise ex) {
        JSONObject obj = new JSONObject();
        JSONArray techniques = new JSONArray();

        // sbuccia i decoratori raccogliendo le tecniche applicate
        Exercise current = ex;
        while (current instanceof ExerciseDecorator ed ) {
            if (current instanceof DropSetDecorator )
                techniques.put("DROP_SET");
            else if (current instanceof RestPauseDecorator )
                techniques.put("REST_PAUSE");
            else if (current instanceof SlowEccentricDecorator )
                techniques.put("SLOW_ECCENTRIC");
            else if (current instanceof IsometricPauseDecorator )
                techniques.put("ISOMETRIC_PAUSE");
            else if (current instanceof ForcedRepsDecorator )
                techniques.put("FORCED_REPS");

            current = ed.getWrapperExercise();
        }

        // current è ora il BaseExercise
        obj.put("name",       current.getName());
        obj.put("target",     current.getTarget());
        obj.put("equipment",  current.getEquipment());
        obj.put("sets",       current.getSets());
        obj.put("reps",       current.getReps());
        obj.put("techniques", techniques);

        return obj;
    }


    public static Exercise deserialize(JSONObject obj) {
        // prima ricostruisci il BaseExercise
        Exercise exercise = new BaseExercise(
                obj.getString("name"),
                obj.getInt("reps"),
                obj.getInt("sets"),
                obj.getString("equipment"),
                obj.getString("target")
        );

        JSONArray techniques = obj.getJSONArray("techniques");
        for (int i = 0; i < techniques.length(); i++) {
            exercise = switch (techniques.getString(i)) {
                case "DROP_SET"        -> new DropSetDecorator(exercise);
                case "REST_PAUSE"      -> new RestPauseDecorator(exercise);
                case "SLOW_ECCENTRIC"  -> new SlowEccentricDecorator(exercise);
                case "ISOMETRIC_PAUSE" -> new IsometricPauseDecorator(exercise);
                case "FORCED_REPS"     -> new ForcedRepsDecorator(exercise);
                default -> exercise; // tecnica sconosciuta, la ignora
            };
        }

        return exercise;
    }
}
