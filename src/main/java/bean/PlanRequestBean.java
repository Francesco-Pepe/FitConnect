package bean;

import model.FitnessGoal;


public class PlanRequestBean {
    int id;
    String email;
    String ptEmail;
    FitnessGoal goal;
    String athlete;
    public PlanRequestBean(String athleteEmail,String ptEmail,FitnessGoal goal){

        this.email=athleteEmail;
        this.ptEmail=ptEmail;
        this.goal=goal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id=id;
    }
    public String getAthlete(){
        return this.athlete;
    }
    public void setAthlete(String athlete){
        this.athlete=athlete;
    }

    public FitnessGoal getGoal() {
        return goal;
    }

    public String getAthleteEmail() {
        return email;
    }

    public String getPtEmail() {
        return ptEmail;
    }
}
