package model;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private int token;
    private Athlete currentAthlete;
    private PersonalTrainer currentPT;
    private TrainingPlan currentTrainingPlan;
    private PlanRequest currentPlanRequest;

    public PlanRequest getCurrentPlanRequest() {
        return currentPlanRequest;
    }

    public void setCurrentPlanRequest(PlanRequest currentPlanRequest) {
        this.currentPlanRequest = currentPlanRequest;
    }

    public TrainingPlan getCurrentTrainingPlan() {
        return currentTrainingPlan;
    }

    public void setCurrentTrainingPlan(TrainingPlan currentTrainingPlan) {
        this.currentTrainingPlan = currentTrainingPlan;
    }

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

