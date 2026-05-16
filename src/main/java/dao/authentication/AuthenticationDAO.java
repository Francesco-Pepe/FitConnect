package dao.authentication;

import exception.DAOException;
import model.Credential;


/**
 * DAO astratto per l'autenticazione di atleti e personal trainer.
 * Fornisce metodi per il login sicuro con password hasate.
 */
public abstract class AuthenticationDAO {
    public abstract Credential getAthleteCredential(String email) throws DAOException;


    public abstract Credential getPersonalTrainerCredential(String email) throws DAOException;

    /**
     * Registra un nuovo atleta con password hasata
     * @param email email dell'atleta
     * @param password password in plaintext
     * @throws DAOException se l'atleta esiste già
     */
    public abstract void registerAthlete(String email, String password) throws DAOException;

    /**
     * Registra un nuovo personal trainer con password hasata
     * @param email email del PT
     * @param password password in plaintext
     * @throws DAOException se il PT esiste già
     */
    public abstract void registerPersonalTrainer(String email, String password) throws DAOException;

    /**
     * Hash della password usando un semplice shift di 2 nell'alfabeto (Cesare cipher)
     * @param password password in plaintext
     * @return password cifrata
     */

}



