package eng;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExerciseSerializer {
    private final static String DROP_SET="DROP_SET";
    private final static  String REST_PAUSE="REST_PAUSE";
    private final static String SLOW_ECCENTRIC="SLOW_ECCENTRIC";
    private final static String ISOMETRIC_PAUSE="ISOMETRIC_PAUSE";
    private final static String FORCED_REPS="FORCED_REPS";
    private ExerciseSerializer(){}

    public static JSONObject serialize(Exercise ex) {
        JSONObject obj = new JSONObject();
        JSONArray techniques = new JSONArray();

        // sbuccia i decoratori raccogliendo le tecniche applicate
        Exercise current = ex;
        while (current instanceof ExerciseDecorator ed ) {
            if (current instanceof DropSetDecorator )
                techniques.put(DROP_SET);
            else if (current instanceof RestPauseDecorator )
                techniques.put(REST_PAUSE);
            else if (current instanceof SlowEccentricDecorator )
                techniques.put(SLOW_ECCENTRIC);
            else if (current instanceof IsometricPauseDecorator )
                techniques.put(ISOMETRIC_PAUSE);
            else if (current instanceof ForcedRepsDecorator )
                techniques.put(FORCED_REPS);

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
                case DROP_SET        -> new DropSetDecorator(exercise);
                case REST_PAUSE     -> new RestPauseDecorator(exercise);
                case SLOW_ECCENTRIC -> new SlowEccentricDecorator(exercise);
                case ISOMETRIC_PAUSE -> new IsometricPauseDecorator(exercise);
                case FORCED_REPS    -> new ForcedRepsDecorator(exercise);
                default -> exercise; // tecnica sconosciuta, la ignora
            };
        }

        return exercise;
    }
 // for DB version
    public static List<String> extractTechniques(Exercise ex){
        List<String> techniques = new ArrayList<>();
        Exercise current = ex;
        while (current instanceof ExerciseDecorator ed ) {
            if (current instanceof DropSetDecorator )
                techniques.add(DROP_SET);
            else if (current instanceof RestPauseDecorator )
                techniques.add(REST_PAUSE);
            else if (current instanceof SlowEccentricDecorator )
                techniques.add(SLOW_ECCENTRIC);
            else if (current instanceof IsometricPauseDecorator )
                techniques.add(ISOMETRIC_PAUSE);
            else if (current instanceof ForcedRepsDecorator )
                techniques.add(FORCED_REPS);

            current = ed.getWrapperExercise();
        }
        return techniques;
    }
    public static Exercise applyTechniques(Exercise base, List<String> techniques) {
        Exercise ex = base;
        for (String t : techniques) {
            ex = switch (t) {
                case DROP_SET        -> new DropSetDecorator(ex);
                case REST_PAUSE      -> new RestPauseDecorator(ex);
                case SLOW_ECCENTRIC  -> new SlowEccentricDecorator(ex);
                case ISOMETRIC_PAUSE -> new IsometricPauseDecorator(ex);
                case FORCED_REPS     -> new ForcedRepsDecorator(ex);
                default -> ex;
            };
        }
        return ex;
    }

}
