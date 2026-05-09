package dao.planrequest;

import eng.DAOFactory;
import exception.DAOException;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FilePlanRequestDAO extends PlanRequestDAO{
    private static final Path FILE_PATH=Path.of("data/requests.json");
    private static final String REQUEST_STATUS ="status";
    private static final String PT_EMAIL ="ptEmail";
    private static final String ATHLETE_EMAIL ="email";
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
            throw new DAOException("Errore lettura richieste", e);
        }
    }

    private void writeFile(JSONArray data) {
            try {
                Files.createDirectories(FILE_PATH.getParent());
                Files.writeString(FILE_PATH, data.toString(2)); // 2 = indentazione leggibile
            } catch (IOException e) {
                throw new DAOException("Errore scrittura file richieste", e);
            }
        }


    @Override
    protected PlanRequest searchRequestById(int id) {
        JSONArray all=readFile();
        for (int i=0;i<all.length();i++){
            JSONObject obj=all.getJSONObject(i);
            if (obj.getInt("id")==id){
                return buildRequest(obj);
            }
        }
        return null;
    }

    private PlanRequest buildRequest(JSONObject obj){
        int id=obj.getInt("id");
        RequestStatus status=RequestStatus.valueOf(obj.getString(REQUEST_STATUS));
        FitnessGoal goal=FitnessGoal.valueOf(obj.getString("goal"));
        String ptEmail=obj.getString(PT_EMAIL);
        String atEmail=obj.getString(ATHLETE_EMAIL);
        PersonalTrainer pt= DAOFactory.getInstance().getPersonalTrainerDAO().getByEmail(ptEmail);
        Athlete a=DAOFactory.getInstance().getAthleteDAO().fetchByEmail(atEmail);
        return new PlanRequest(id,a,pt,goal,status);

    }

    private JSONObject serializeRequest(PlanRequest request){
        JSONObject obj=new JSONObject();
        obj.put("id",request.getId());
        obj.put(REQUEST_STATUS,request.getStatus().name());
        obj.put("goal",request.getGoal().name());
        obj.put(PT_EMAIL,request.getPt().getEmail());
        obj.put(ATHLETE_EMAIL,request.getClient().getEmail());
        return obj;
    }


    @Override
    public void save(PlanRequest request) {
        JSONArray all=readFile();
        int maxId=0;
        for (int i=0;i<all.length();i++){
            JSONObject obj=all.getJSONObject(i);
            int id=obj.getInt("id");
            if (id>maxId){
                maxId= id;
            }
        }
        request.setId(maxId+1);
        all.put(serializeRequest(request));
        writeFile(all);
        addToCache(request);
    }

    @Override
    public void update(PlanRequest request) {
        JSONArray all=readFile();
        boolean found=false;
        for (int i=0;i<all.length();i++){
            JSONObject obj=all.getJSONObject(i);
            if (obj.getInt("id")==request.getId()){
                found=true;
                all.put(i,serializeRequest(request));
                writeFile(all);
                break;
            }
        }
        if (!found){
            throw new DAOException("Richiesta non trovata");
        }
    }

    @Override
    public List<PlanRequest> fetchByAthlete(String athleteEmail) {
        List<PlanRequest> requests=new ArrayList<>();
        JSONArray all=readFile();
        for (int i=0;i<all.length();i++){
            JSONObject obj=all.getJSONObject(i);
            if (obj.getString(ATHLETE_EMAIL).equals(athleteEmail)){
                requests.add(getById(obj.getInt("id")));
            }
        }
        return requests;
    }

    @Override
    public List<PlanRequest> fetchPendingByTrainer(String trainerEmail) {
        List<PlanRequest> pending=new ArrayList<>();
        JSONArray all=readFile();
        for (int i=0;i<all.length();i++){
            JSONObject obj=all.getJSONObject(i);
            if(obj.getString(PT_EMAIL).equals(trainerEmail) && obj.getString(REQUEST_STATUS).equals(RequestStatus.PENDING.name())){
                    pending.add(getById(obj.getInt("id")));
            }
        }
        return pending;
    }
}
