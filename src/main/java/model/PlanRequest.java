package model;

public class PlanRequest {
    private int id;
    private RequestStatus status;
    private Athlete client;
    private PersonalTrainer pt;
    private FitnessGoal goal;

    public PlanRequest(int id,Athlete client,PersonalTrainer pt,FitnessGoal goal){
        this.id=id;
        this.status=RequestStatus.PENDING;
        this.client=client;
        this.pt=pt;
        this.goal=goal;
    }

    public PlanRequest(int id,Athlete client,PersonalTrainer pt,FitnessGoal goal,RequestStatus status){
        this.id=id;
        this.status=status;
        this.client=client;
        this.pt=pt;
        this.goal=goal;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
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
