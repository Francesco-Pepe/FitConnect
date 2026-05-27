package dao.athlete;

import eng.CachedDAO;
import model.Athlete;

import java.util.List;

public abstract class AthleteDAO extends CachedDAO<Athlete> {
    @Override
    public String fetchKey(Athlete a) {
        return a.getEmail();
    }

    public Athlete fetchByEmail(String email) {
        Athlete a;
        if (inCache(email)) {
            a = fetchFromCache(email);
        } else {
            a = searchAthleteByEmail(email);
            if (a != null) {
                addToCache(a);
            }
        }
        return a;

    }

    public abstract Athlete searchAthleteByEmail(String email);

    //for the trainer to see his athletes and their current plan,for another UC,implemented soon(i hope)
    public abstract List<Athlete> fetchAthleteByTrainer(String ptEmail);

    public abstract void update(Athlete a);
}