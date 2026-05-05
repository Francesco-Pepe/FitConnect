package dao;

import model.PersonalTrainer;

public interface PersonalTrainerDAO {
    void save(PersonalTrainer pt);
    void update(PersonalTrainer pt);
    PersonalTrainer findByEmail(String email);
}
