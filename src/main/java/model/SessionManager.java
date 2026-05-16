package model;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    private static SessionManager me = null;

    private Map<Integer,Session> sessions;

    private int tokens = 0;


    private SessionManager() {
        this.sessions = new HashMap<>();
    }

    public static synchronized SessionManager getInstance() {
        if (me == null) {
            me =  new SessionManager();
        }
        return me;
    }

    public synchronized Session createSession(Athlete athlete) {
        this.tokens++;
        int token = this.tokens;

        Session newSession = new Session();
        newSession.setToken(token);
        newSession.setCurrentAthlete(athlete);

        this.sessions.put(token, newSession);
        return newSession;
    }


    public synchronized Session createSession(PersonalTrainer pt) {

        this.tokens++;
        int token = this.tokens;

        Session newSession = new Session();
        newSession.setToken(token);

        newSession.setCurrentPT(pt);

        this.sessions.put(token, newSession);

        return newSession;
    }

    public synchronized Session getSession(int token) {
        return this.sessions.get(token);
    }

    public synchronized void deleteSession(int token) {
        if (this.sessions.containsKey(token)) {
            this.sessions.remove(token);
        }
    }
}

