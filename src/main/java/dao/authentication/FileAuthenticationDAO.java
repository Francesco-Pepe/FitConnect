package dao.authentication;

import eng.DAOFactory;
import exception.DAOException;
import model.Athlete;
import model.Credential;
import model.PersonalTrainer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileAuthenticationDAO extends AuthenticationDAO {
    private static final Path ATHLETES_CREDS_PATH = Path.of("data/athletes_credentials.json");
    private static final Path TRAINERS_CREDS_PATH = Path.of("data/trainers_credentials.json");

    private JSONArray readCredentialsFile(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                return new JSONArray();
            }
            String content = Files.readString(path);
            return new JSONArray(content);
        } catch (IOException e) {
            throw new DAOException("Errore lettura credenziali", e);
        }
    }

    private void writeCredentialsFile(Path path, JSONArray data) {
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, data.toString(2));
        } catch (IOException e) {
            throw new DAOException("Errore scrittura credenziali", e);
        }
    }

    @Override
    public Credential getAthleteCredential(String email) throws DAOException {
        JSONArray creds = readCredentialsFile(ATHLETES_CREDS_PATH);

        for (int i = 0; i < creds.length(); i++) {
            JSONObject obj = creds.getJSONObject(i);
            if (obj.getString("email").equals(email)) {
                String hash = obj.getString("passwordHash");
                Credential cred =new Credential(email,hash);
                return cred;
            }
        }

        throw new DAOException("Atleta non trovato: " + email);
    }

    @Override
    public Credential getPersonalTrainerCredential(String email) throws DAOException {
        JSONArray creds = readCredentialsFile(TRAINERS_CREDS_PATH);

        for (int i = 0; i < creds.length(); i++) {
            JSONObject obj = creds.getJSONObject(i);
            if (obj.getString("email").equals(email)) {
                String hash = obj.getString("passwordHash");
                Credential cred=new Credential(email,hash);
                return cred;
            }
        }

        throw new DAOException("Personal trainer non trovato: " + email);
    }

    @Override
    public void registerAthlete(String email, String password) throws DAOException {
  //nothing to do
    }

    @Override
    public void registerPersonalTrainer(String email, String password) throws DAOException {
      //nothing to do
    }
}

