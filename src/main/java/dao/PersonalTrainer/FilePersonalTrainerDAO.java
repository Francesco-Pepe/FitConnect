package dao.PersonalTrainer;

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
        String email= obj.getString("email");
        String name=obj.getString("name");
        String surname=obj.getString("surname");
        Gender gender=Gender.valueOf(obj.getString("gender"));
        PersonalTrainer pt=new PersonalTrainer(email,name,surname,gender);
        addToCache(pt);
        if (obj.getString("clients").equals("true")) {
            List<Athlete> clients = DAOFactory.getInstance().getAthleteDAO().fetchAthleteByTrainer(pt.getEmail());
            pt.setClients(clients);
        }
        if (obj.getString("requests").equals("true")) {
            List<PlanRequest> reqs = DAOFactory.getInstance().getPlanRequestDAO().fetchPendingByTrainer(pt.getEmail());
            pt.setRequests(reqs);
        }
        return pt;
    }

    public JSONObject serializePt(PersonalTrainer pt){
        JSONObject obj=new JSONObject();
        obj.put("email",pt.getEmail());
        obj.put("name",pt.getName());
        obj.put("surname",pt.getSurname());
        obj.put("gender",pt.getGender().name());
        obj.put("clients",pt.getClients().toArray().length>0 ? "true" : "false");
        obj.put("requests",pt.getRequests().size()>0 ? "true": "false");

        return obj;
    }



    @Override
    public PersonalTrainer searchPtByEmail(String email) {
        JSONArray all=readFile();
        for (int i=0;i<all.length();i++){
            JSONObject obj=all.getJSONObject(i);
            if (obj.getString("email").equals(email)){
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
            String email=obj.getString("email");
            pts.add(getByEmail(email));
        }
        return pts;
    }
    public void save(PersonalTrainer pt) {
        JSONArray all = readFile();
        for (int i = 0; i < all.length(); i++) {
            if (all.getJSONObject(i).getString("email").equals(pt.getEmail())) {
                throw new DAOException("PT già esistente: " + pt.getEmail());
            }
        }
        all.put(serializePt(pt));
        writeFile(all);
        addToCache(pt);
    }

    @Override
    public void update(PersonalTrainer pt) {
        JSONArray all=readFile();
        boolean found=false;
        for (int i=0;i<all.length();i++){
            JSONObject obj=all.getJSONObject(i);
            if (obj.getString("email").equals(pt.getEmail())){
                all.put(i,serializePt(pt));
                found=true;
                break;
            }
        }
        if (!found){
            throw new DAOException("Pt not found for "+ pt.getEmail());
        }
        writeFile(all);
    }
}