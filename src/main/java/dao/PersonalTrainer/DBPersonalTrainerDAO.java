package dao.PersonalTrainer;

import model.PersonalTrainer;

import java.util.List;

public class DBPersonalTrainerDAO extends PersonalTrainerDAO{
    @Override
    public PersonalTrainer searchPtByEmail(String email) {
        return null;
    }

    @Override
    public List<PersonalTrainer> fetchAll() {
        return List.of();
    }

    @Override
    public void update(PersonalTrainer pt) {

    }
}
