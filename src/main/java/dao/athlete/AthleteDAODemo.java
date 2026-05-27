package dao.athlete;

import model.Athlete;
import model.Gender;

import java.util.List;

public class AthleteDAODemo extends AthleteDAO{
    @Override
    public Athlete searchAthleteByEmail(String email) {
        return new Athlete("frank@gmail.com","Francesco","Pepe",78,178, Gender.MALE);
    }

    @Override
    public List<Athlete> fetchAthleteByTrainer(String ptEmail) {
        if (ptEmail.equals("trainer@fit.com")) {
            return List.of(
                    new Athlete("frank@gmail.com", "Francesco", "Pepe", 78, 178, Gender.MALE),
                    new Athlete("sara@gmail.com", "Sara", "Bianchi", 62, 168, Gender.FEMALE)
            );
        }
        // Se accede un altro PT, per la demo non ha atleti assegnati
        return List.of();
    }

    @Override
    public void update(Athlete a) {
        //nothing to do,changes are already in ram
    }
}
