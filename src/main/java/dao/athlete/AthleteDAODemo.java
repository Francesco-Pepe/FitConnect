package dao.athlete;

import model.Athlete;
import model.Gender;
import model.PhysicalTraits;

import java.util.List;

public class AthleteDAODemo extends AthleteDAO{
    @Override
    public Athlete searchAthleteByEmail(String email) {
        PhysicalTraits traits=new PhysicalTraits(78,178,Gender.MALE);
        return new Athlete("frank@gmail.com","Francesco","Pepe",traits);
    }

    @Override
    public List<Athlete> fetchAthleteByTrainer(String ptEmail) {
        PhysicalTraits trait1=new PhysicalTraits(78,178,Gender.MALE);
        PhysicalTraits trait2=new PhysicalTraits(62,168,Gender.FEMALE);
        if (ptEmail.equals("trainer@fit.com")) {
            return List.of(
                    new Athlete("frank@gmail.com", "Francesco", "Pepe",trait1),
                    new Athlete("sara@gmail.com", "Sara", "Bianchi",trait2)
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
