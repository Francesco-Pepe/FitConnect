package dao.Athlete;

import model.Athlete;

import java.util.List;

public class DBAthleteDAO extends AthleteDAO{
    @Override
    public Athlete searchAthleteByEmail(String email) {
        return null;
    }

    @Override
    public List<Athlete> fetchAthleteByTrainer(String ptEmail) {
        return List.of();
    }

    @Override
    public void update(Athlete a) {

    }
}
