package dao.athlete;

import dao.personaltrainer.PersonalTrainerDAO;
import dao.trainingplan.TrainingPlanDAO;
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
    private  static final String Email ="email";
    private  static final String Pt ="ptEmail";
    // Tutti gli atleti stanno in un unico file
    private static final Path FILE_PATH = Path.of("data/athletes.json");

    // ==========================================
    // LETTURA — metodi astratti da implementare
    // ==========================================

    @Override
    public Athlete searchAthleteByEmail(String email) {
        JSONArray all = readFile();
        for (int i = 0; i < all.length(); i++) {
            JSONObject obj = all.getJSONObject(i);
            if (obj.getString(Email).equals(email)) {
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
            // salviamo solo l'email del PT nel file — confrontiamo quella
            if (obj.optString(Pt, "").equals(ptEmail)) {
                Athlete a = fetchByEmail(obj.getString(Email)); // passa per la cache
                result.add(a);
            }
        }
        return result;
    }

    // ==========================================
    // SCRITTURA
    // ==========================================

    // Salva un nuovo atleta — usato alla registrazione
    public void save(Athlete athlete) {
        JSONArray all = readFile();

        // controlla che non esista già
        for (int i = 0; i < all.length(); i++) {
            if (all.getJSONObject(i).getString(Email).equals(athlete.getEmail())) {
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
            if (all.getJSONObject(i).getString(Email).equals(athlete.getEmail())) {
                all.put(i, serializeAthlete(athlete)); // sostituisce il vecchio oggetto
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

    // ==========================================
    // SERIALIZZAZIONE — oggetto Java → JSON
    // ==========================================

    private JSONObject serializeAthlete(Athlete a) {
        JSONObject obj = new JSONObject();
        obj.put(Email,   a.getEmail());
        obj.put("name",    a.getName());
        obj.put("surname", a.getSurname());
        obj.put("weight",  a.getWeight());
        obj.put("height",  a.getHeight());
        obj.put("gender",  a.getGender().name()); // salva "MALE" o "FEMALE"

        // salviamo solo le chiavi delle relazioni, non gli oggetti interi
        obj.put(Pt,   a.getPt()   != null ? a.getPt().getEmail()            : JSONObject.NULL);
        obj.put("hasPlan",   a.getPlan() != null); // basta sapere se esiste, lo carica TrainingPlanDAO

        return obj;
    }

    // ==========================================
    // DESERIALIZZAZIONE — JSON → oggetto Java
    // ==========================================

    private Athlete buildAthlete(JSONObject obj) {
        // 1. costruisci l'atleta con i dati semplici
        Athlete athlete = new Athlete(
                obj.getString(Email),
                obj.getString("name"),
                obj.getString("surname"),
                obj.getDouble("weight"),
                obj.getInt("height"),
                Gender.valueOf(obj.getString("gender"))
        );

        // 2. aggiungi subito in cache PRIMA di chiamare gli altri DAO
        //    questo evita loop infiniti se PersonalTrainerDAO richiama AthleteDAO
        addToCache(athlete);

        // 3. collega il PT se presente
        String ptEmail = obj.optString(Pt, "");
        if (!ptEmail.isEmpty()) {
            PersonalTrainerDAO ptDAO = DAOFactory.getInstance().getPersonalTrainerDAO();
            PersonalTrainer pt = ptDAO.getByEmail(ptEmail);
            athlete.setPt(pt);
        }

        // 4. collega il piano se esiste
        if (obj.optBoolean("hasPlan", false)) {
            TrainingPlanDAO planDAO = DAOFactory.getInstance().getTrainingPlanDAO();
            TrainingPlan plan = planDAO.fetchByAthlete(athlete.getEmail());
            athlete.setPlan(plan);
        }

        return athlete;
    }

    // ==========================================
    // UTILITY — lettura e scrittura file
    // ==========================================

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