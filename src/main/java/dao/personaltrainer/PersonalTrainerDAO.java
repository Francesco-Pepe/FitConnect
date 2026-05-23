package dao.personaltrainer;

import eng.CachedDAO;
import model.PersonalTrainer;

import java.util.List;

public abstract class PersonalTrainerDAO extends CachedDAO<PersonalTrainer> {
    @Override
    public String fetchKey(PersonalTrainer pt){
        return pt.getEmail();
    }
    public PersonalTrainer getByEmail(String email){
        PersonalTrainer p;
        if (inCache(email)){
            p=fetchFromCache(email);
        }
        else {
            p=searchPtByEmail(email);
            if (p!=null){
                addToCache(p);
            }
        }

        return p;
    }
    public abstract PersonalTrainer searchPtByEmail(String email);
    public abstract List<PersonalTrainer> fetchAll();

}
