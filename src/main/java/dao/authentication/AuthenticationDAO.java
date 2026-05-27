package dao.authentication;

import exception.DAOException;
import model.Credential;

public abstract class AuthenticationDAO {
    public abstract Credential getAthleteCredential(String email) throws DAOException;
    public abstract Credential getPersonalTrainerCredential(String email) throws DAOException;

    //not implemented yet
    public abstract void registerAthlete(String email, String password) throws DAOException;

    //not implemented yet
    public abstract void registerPersonalTrainer(String email, String password) throws DAOException;


}



