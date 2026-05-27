package dao.personaltrainer;

import dao.athlete.AthleteDAO;

import eng.DAOFactory;
import model.Gender;
import model.PersonalTrainer;

import java.util.List;

public class PersonalTrainerDAODemo extends PersonalTrainerDAO {

    private final AthleteDAO dao = DAOFactory.getInstance().getAthleteDAO();

    @Override
    public PersonalTrainer searchPtByEmail(String email){
        PersonalTrainer p = null;
        if (email.equalsIgnoreCase("trainer@fit.com")){
            p = new PersonalTrainer(email, "Mario", "Rossi", Gender.MALE);
        }
        return p;
    }

    @Override
    public List<PersonalTrainer> fetchAll(){
        PersonalTrainer mario = searchPtByEmail("trainer@fit.com");
        return mario != null ? List.of(mario) : List.of();
    }


}