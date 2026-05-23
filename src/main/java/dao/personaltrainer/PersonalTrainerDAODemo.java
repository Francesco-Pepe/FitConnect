package dao.personaltrainer;

import dao.athlete.AthleteDAO;

import eng.DAOFactory;
import model.Gender;
import model.PersonalTrainer;

import java.util.List;

public class PersonalTrainerDAODemo extends PersonalTrainerDAO {

    // CORREZIONE 1: Inizializziamo il DAO!
    private final AthleteDAO dao = DAOFactory.getInstance().getAthleteDAO();//usa DAOFactory!!!!

    @Override
    public PersonalTrainer searchPtByEmail(String email){
        PersonalTrainer p = null;
        if (email.equalsIgnoreCase("trainer@fit.com")){
            p = new PersonalTrainer(email, "Mario", "Rossi", Gender.MALE);

            // Ora questo funziona perfettamente
        }
        return p;
    }

    @Override
    public List<PersonalTrainer> fetchAll(){
        // CORREZIONE 2: Restituiamo una vera lista contenente il nostro PT di test!
        // Così la GUI della registrazione o della richiesta funzionerà alla grande.
        PersonalTrainer mario = searchPtByEmail("trainer@fit.com");
        return mario != null ? List.of(mario) : List.of();
    }


}