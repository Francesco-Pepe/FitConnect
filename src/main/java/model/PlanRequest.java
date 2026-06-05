package model;

public class PlanRequest {
    private int id;
    private RequestStatus status;
    private final FitnessGoal goal;
    private PersonalTrainer pt;
    private Athlete athlete;

    public PlanRequest(int id, RequestStatus status, FitnessGoal goal, PersonalTrainer pt, Athlete athlete) {
        this.id = id;
        this.status = status;
        this.goal = goal;
        this.pt = pt;
        this.athlete = athlete;
    }
    public PlanRequest(int id,  FitnessGoal goal, PersonalTrainer pt, Athlete athlete) {
        this.id = id;
        this.status = RequestStatus.PENDING;
        this.goal = goal;
        this.pt = pt;
        this.athlete = athlete;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public PersonalTrainer getPt() {
        return pt;
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




    public RequestStatus getStatus() {
        return status;
    }


    public void accept(){
        this.status=RequestStatus.ACCEPTED;
    }
    public void decline(){
        this.status=RequestStatus.REJECTED;
    }


}
