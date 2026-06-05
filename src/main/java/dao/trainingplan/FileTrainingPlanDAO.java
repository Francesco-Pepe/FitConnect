package dao.trainingplan;

import eng.ExerciseSerializer;
import exception.DAOException;
import model.Exercise;
import model.TrainingPlan;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileTrainingPlanDAO extends TrainingPlanDAO{
    private static final Path FILE_PATH=Path.of("data/plans.json");
    private static final String ATHLETE_EMAIL ="athlete";
    private JSONArray readFile() {
        try {
            if (!Files.exists(FILE_PATH)) {
                // se il file non esiste ancora, restituisce un array vuoto
                Files.createDirectories(FILE_PATH.getParent());
                return new JSONArray();
            }
            String content = Files.readString(FILE_PATH);
            return new JSONArray(content);
        } catch (IOException e) {
            throw new DAOException("Errore lettura piani", e);
        }
    }
    private void writeFile(JSONArray data) {
        try {
            Files.createDirectories(FILE_PATH.getParent());
            Files.writeString(FILE_PATH, data.toString(2)); // 2 = indentazione leggibile
        } catch (IOException e) {
            throw new DAOException("Errore scrittura file piani", e);
        }
    }

    private JSONObject serializePlan(TrainingPlan plan){
        JSONObject obj=new JSONObject();
        obj.put(ATHLETE_EMAIL,fetchKey(plan));
        obj.put("expiration",plan.getExpirationDate().toString());
        obj.put("creation",plan.getCreationDate().toString());
        JSONArray exercises=new JSONArray();
        for (Exercise ex:plan.getExercises()){
            exercises.put(ExerciseSerializer.serialize(ex));
        }
        obj.put("exercises",exercises);
        return obj;
    }

    private TrainingPlan buildPlan(JSONObject obj){
        LocalDate creation=LocalDate.parse(obj.getString("creation"));
        LocalDate expiration=LocalDate.parse(obj.getString("expiration"));

        JSONArray exercises=obj.getJSONArray("exercises");
        List<Exercise> exList=new ArrayList<>();
        for (int i=0;i<exercises.length();i++){
            Exercise ex=ExerciseSerializer.deserialize(exercises.getJSONObject(i));
            exList.add(ex);
        }
        //bo Athlete,the linking will be done by AthleteService
        return new TrainingPlan(null,creation,expiration,exList);

    }



    @Override
    public TrainingPlan searchByAthlete(String atEmail) {
        JSONArray all=readFile();
        for (int i=0;i<all.length();i++){
            JSONObject obj= all.getJSONObject(i);
            if (obj.getString(ATHLETE_EMAIL).equals(atEmail)){
                return buildPlan(obj);
            }
        }
        return null;
    }


    @Override
    public void save(TrainingPlan plan) {
        JSONArray all = readFile();
        all.put(serializePlan(plan));
        writeFile(all);
    }
    public void update(TrainingPlan plan) {
        JSONArray all = readFile();
        boolean found = false;
        for (int i = 0; i < all.length(); i++) {
            if (all.getJSONObject(i).getString(ATHLETE_EMAIL)
                    .equals(fetchKey(plan))) {
                all.put(i, serializePlan(plan));
                found = true;
                break;
            }
        }
        if (!found) throw new DAOException("Piano non trovato per update");
        writeFile(all);
    }
}
