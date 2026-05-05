package dao;

import model.Athlete;

public interface AthleteDAO {
    void save(Athlete client);
    void update(Athlete client);
    Athlete findByEmail(String email);
}
