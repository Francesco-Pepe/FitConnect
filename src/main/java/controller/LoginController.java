package controller;

import bean.AthleteBean;
import bean.PersonalTrainerBean;
import bean.SessionBean;
import dao.athlete.AthleteDAO;
import dao.authentication.AuthenticationDAO;
import dao.personaltrainer.PersonalTrainerDAO;
import eng.DAOFactory;
import eng.PasswordEncoder;
import exception.ControllerException;
import exception.DAOException;
import exception.InvalidCredentialsException;
import model.*;


public class LoginController {

        public SessionBean logAsAthlete(AthleteBean athlete){
            try {
                AuthenticationDAO authDAO = DAOFactory.getInstance().getAuthenticationDAO();
                AthleteDAO athleteDAO = DAOFactory.getInstance().getAthleteDAO();
                String email=athlete.getEmail();
                String password=athlete.getPassword();
                Credential creds = authDAO.getAthleteCredential(email);
                String hash =creds.getHashPassword();
                if (PasswordEncoder.verifyPassword(password,hash)){
                    Athlete a=athleteDAO.fetchByEmail(email);
                    Session newSession= SessionManager.getInstance().createSession(a);
                    athlete.setName(a.getName());
                    athlete.setSurname(a.getSurname());
                    if(a.getPt()!=null){
                        String ptName=a.getPt().getName();
                        String ptSurname=a.getPt().getSurname();
                        athlete.setTrainer(ptName+ " " +ptSurname);
                    }
                   return new SessionBean(athlete, newSession.getToken());

                }

            }catch (DAOException e){
                throw new InvalidCredentialsException("Credentials not valid");
            }
            throw new ControllerException("login fallito");
        }

        public SessionBean logAsPersonalTrainer(PersonalTrainerBean pt){

            try {
                AuthenticationDAO authDAO = DAOFactory.getInstance().getAuthenticationDAO();
                PersonalTrainerDAO  ptDAO= DAOFactory.getInstance().getPersonalTrainerDAO();
                String email=pt.getEmail();
                String password=pt.getPassword();
                Credential creds = authDAO.getPersonalTrainerCredential(email);
                String hash =creds.getHashPassword();
                if (PasswordEncoder.verifyPassword(password,hash)){
                    PersonalTrainer p=ptDAO.getByEmail(email);
                    Session newSession=SessionManager.getInstance().createSession(p);
                    pt.setName(p.getName());
                    pt.setSurname(p.getSurname());
                    return new SessionBean(pt, newSession.getToken());

                }

            }catch (DAOException e){
                throw new InvalidCredentialsException("Credentials not valid");
            }
            throw new ControllerException("login fallito");

        }

}
