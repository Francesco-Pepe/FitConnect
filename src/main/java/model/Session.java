package model;



public class Session {
    private int token;
    private Athlete currentAthlete;
    private PersonalTrainer currentPT;

    public PersonalTrainer getCurrentPT() {
        return currentPT;
    }

    public void setCurrentPT(PersonalTrainer currentPT) {
        this.currentPT = currentPT;
    }

    public Athlete getCurrentAthlete() {
        return currentAthlete;
    }

    public void setCurrentAthlete(Athlete currentAthlete) {
        this.currentAthlete = currentAthlete;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}

