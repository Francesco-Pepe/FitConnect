package view;

import bean.*;


import java.util.List;

public class Context {
    private SessionBean session;
    private AthleteBean athlete;
    private PersonalTrainerBean pt;
    private List<AthleteBean> athletes;
    private TrainingPlanBean plan;
    private String trainer;
    private PlanRequestBean request;
    public Context(){}

    public Context(AthleteBean athlete){
        this.athlete=athlete;
    }

    public Context(PersonalTrainerBean pt){
        this.pt=pt;
    }

    public PlanRequestBean getRequest(){
        return this.request;
    }

    public void setRequest(PlanRequestBean request) {
        this.request = request;
    }

    public String getTrainer(){
        return this.trainer;
    }

    public void setTrainer(String trainer){
        this.trainer=trainer;
    }

    public TrainingPlanBean getPlan() {
        return plan;
    }

    public void setPlan(TrainingPlanBean plan) {
        this.plan = plan;
    }

    public List<AthleteBean> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<AthleteBean> athletes) {
        this.athletes = athletes;
    }

    public PersonalTrainerBean getPt() {
        return pt;
    }

    public void setPt(PersonalTrainerBean pt) {
        this.pt = pt;
    }

    public AthleteBean getAthlete() {
        return athlete;
    }

    public void setAthlete(AthleteBean athlete) {
        this.athlete = athlete;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }




}
