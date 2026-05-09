package dao.Athlete;

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
    public abstract List<Athlete> fetchAthleteByTrainer(String ptEmail);
    public abstract void update(Athlete a);
}