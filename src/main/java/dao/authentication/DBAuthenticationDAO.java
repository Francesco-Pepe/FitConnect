package dao.authentication;

import exception.DAOException;

import model.Credential;


/**
 * Implementazione database dell'AuthenticationDAO.
 * Placeholder per futura integrazione con database.
 */
public class DBAuthenticationDAO extends AuthenticationDAO {

    @Override
    public Credential getAthleteCredential(String email) throws DAOException {
        throw new DAOException("DBAuthenticationDAO non ancora implementato");
    }

    @Override
    public Credential getPersonalTrainerCredential(String email) throws DAOException {
        throw new DAOException("DBAuthenticationDAO non ancora implementato");
    }

    @Override
    public void registerAthlete(String email, String password) throws DAOException {
        throw new DAOException("DBAuthenticationDAO non ancora implementato");
    }

    @Override
    public void registerPersonalTrainer(String email, String password) throws DAOException {
        throw new DAOException("DBAuthenticationDAO non ancora implementato");
    }
}

