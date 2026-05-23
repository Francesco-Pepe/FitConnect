package dao.personaltrainer;

import eng.DAOFactory;
import exception.DAOException;
import model.Athlete;
import model.Gender;
import model.PersonalTrainer;
import model.PlanRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FilePersonalTrainerDAO extends PersonalTrainerDAO{
    private static final Path FILE_PATH=Path.of("data/pt.json");
    private static final String PT_EMAIL ="email";
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
            throw new DAOException("Errore lettura file pt", e);
        }
    }

    private void writeFile(JSONArray data) {
        try {
            Files.createDirectories(FILE_PATH.getParent());
            Files.writeString(FILE_PATH, data.toString(2)); // 2 = indentazione leggibile
        } catch (IOException e) {
            throw new DAOException("Errore scrittura file pt", e);
        }
    }


    public PersonalTrainer buildPt(JSONObject obj){
        String email= obj.getString(PT_EMAIL);
        String name=obj.getString("name");
        String surname=obj.getString("surname");
        Gender gender=Gender.valueOf(obj.getString("gender"));
        PersonalTrainer pt=new PersonalTrainer(email,name,surname,gender);
        addToCache(pt);
        return pt;
    }

    public JSONObject serializePt(PersonalTrainer pt){
        JSONObject obj=new JSONObject();
        obj.put(PT_EMAIL,pt.getEmail());
        obj.put("name",pt.getName());
        obj.put("surname",pt.getSurname());
        obj.put("gender",pt.getGender().name());
        return obj;
    }



    @Override
    public PersonalTrainer searchPtByEmail(String email) {
        JSONArray all=readFile();
        for (int i=0;i<all.length();i++){
            JSONObject obj=all.getJSONObject(i);
            if (obj.getString(PT_EMAIL).equals(email)){
                return buildPt(obj);
            }
        }
        return null;
    }

    @Override
    public List<PersonalTrainer> fetchAll() {
        JSONArray all=readFile();
        List<PersonalTrainer> pts=new ArrayList<>();
        for (int i=0;i<all.length();i++){
            JSONObject obj=all.getJSONObject(i);
            String email=obj.getString(PT_EMAIL);
            pts.add(getByEmail(email));
        }
        return pts;
    }
    public void save(PersonalTrainer pt) {
        JSONArray all = readFile();
        for (int i = 0; i < all.length(); i++) {
            if (all.getJSONObject(i).getString(PT_EMAIL).equals(pt.getEmail())) {
                throw new DAOException("PT già esistente: " + pt.getEmail());
            }
        }
        all.put(serializePt(pt));
        writeFile(all);
        addToCache(pt);
    }


}