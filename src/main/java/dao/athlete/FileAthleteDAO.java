package dao.athlete;

import dao.personaltrainer.PersonalTrainerDAO;
import eng.AthleteService;
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

public class FileAthleteDAO extends AthleteDAO {
    private  static final String ATHLETE_EMAIL ="email";
    private  static final String PT_EMAIL ="ptEmail";
    // Tutti gli atleti stanno in un unico file
    private static final Path FILE_PATH = Path.of("data/athletes.json");

    @Override
    public Athlete searchAthleteByEmail(String email) {
        JSONArray all = readFile();
        for (int i = 0; i < all.length(); i++) {
            JSONObject obj = all.getJSONObject(i);
            if (obj.getString(ATHLETE_EMAIL).equals(email)) {
                return buildAthlete(obj);
            }
        }
        return null; // atleta non trovato
    }

    @Override
    public List<Athlete> fetchAthleteByTrainer(String ptEmail) {
        JSONArray all = readFile();
        List<Athlete> result = new ArrayList<>();
        for (int i = 0; i < all.length(); i++) {
            JSONObject obj = all.getJSONObject(i);
            // salvo solo l'email del PT nel file
            if (obj.optString(PT_EMAIL, "").equals(ptEmail)) {
                Athlete a = fetchByEmail(obj.getString(ATHLETE_EMAIL)); // passa per la cache
                result.add(a);
            }
        }
        return result;
    }


    //For registration,not implemented yet
    //Note:the athlete file should be compliiant with the credential file
    public void save(Athlete athlete) {
        JSONArray all = readFile();

        // existence check
        for (int i = 0; i < all.length(); i++) {
            if (all.getJSONObject(i).getString(ATHLETE_EMAIL).equals(athlete.getEmail())) {
                throw new DAOException("Atleta già esistente: " + athlete.getEmail());
            }
        }
        all.put(serializeAthlete(athlete));
        writeFile(all);
        addToCache(athlete);
    }

    @Override
    public void update(Athlete athlete) {
        JSONArray all = readFile();
        boolean found = false;

        for (int i = 0; i < all.length(); i++) {
            if (all.getJSONObject(i).getString(ATHLETE_EMAIL).equals(athlete.getEmail())) {
                all.put(i, serializeAthlete(athlete));
                found = true;
                break;
            }
        }

        if (!found) {
            throw new DAOException("Atleta non trovato per update: " + athlete.getEmail());
        }

        writeFile(all);
        // la cache ha già l'oggetto aggiornato (è lo stesso riferimento in RAM)
    }


    private JSONObject serializeAthlete(Athlete a) {
        JSONObject obj = new JSONObject();
        obj.put(ATHLETE_EMAIL,   a.getEmail());
        obj.put("name",    a.getName());
        obj.put("surname", a.getSurname());
        obj.put("weight",  a.getWeight());
        obj.put("height",  a.getHeight());
        obj.put("gender",  a.getGender().name()); // salva "MALE" o "FEMALE"
        obj.put(PT_EMAIL,   a.getPt()   != null ? a.getPt().getEmail()            : JSONObject.NULL);
        obj.put("hasPlan",   a.getPlan() != null);

        return obj;
    }

    private Athlete buildAthlete(JSONObject obj) {
        String email   = obj.getString(ATHLETE_EMAIL);
        String ptEmail = obj.optString(PT_EMAIL, "");

        PersonalTrainer pt   = null;
        TrainingPlan    plan = null;

        if (!ptEmail.isEmpty()) {
            PersonalTrainerDAO ptDAO   = DAOFactory.getInstance().getPersonalTrainerDAO();
            pt   = ptDAO.fetchPtByEmail(ptEmail);
        }
        PhysicalTraits traits=new PhysicalTraits(
                obj.getDouble("weight"),
                obj.getInt("height"),
                Gender.valueOf(obj.getString("gender"))
        );
        Athlete a=new Athlete(
                email,
                obj.getString("name"),
                obj.getString("surname"),
                traits,
                null,    // può essere null
                null   // può essere null
        );
        return AthleteService.getAthleteWithPlan(a,pt);
    }


    private JSONArray readFile() {
        try {
            if (!Files.exists(FILE_PATH)) {
                Files.createDirectories(FILE_PATH.getParent());
                return new JSONArray();
            }
            String content = Files.readString(FILE_PATH);
            return new JSONArray(content);
        } catch (IOException e) {
            throw new DAOException("Errore lettura file atleti", e);
        }
    }

    private void writeFile(JSONArray data) {
        try {
            Files.createDirectories(FILE_PATH.getParent());
            Files.writeString(FILE_PATH, data.toString(2)); // 2 = indentazione leggibile
        } catch (IOException e) {
            throw new DAOException("Errore scrittura file atleti", e);
        }
    }
}