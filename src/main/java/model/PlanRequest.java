package model;

public class PlanRequest {
    private RequestStatus status;
    private Athlete client;
    private PersonalTrainer pt;
    private FitnessGoal goal;

    public PlanRequest(Athlete client,PersonalTrainer pt,FitnessGoal goal){
        this.status=RequestStatus.PENDING;
        this.client=client;
        this.pt=pt;
        this.goal=goal;
    }

    public FitnessGoal getGoal() {
        return goal;
    }

    public void setGoal(FitnessGoal goal) {
        this.goal = goal;
    }

    public PersonalTrainer getPt() {
        return pt;
    }

    public void setPt(PersonalTrainer pt) {
        this.pt = pt;
    }

    public Athlete getClient() {
        return client;
    }

    public void setClient(Athlete client) {
        this.client = client;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
