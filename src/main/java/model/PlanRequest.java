package model;

public class PlanRequest {
    private int id;
    private RequestStatus status;
    private String clientEmail;
    private String ptEmail;
    private FitnessGoal goal;

    public PlanRequest(int id,String client,String pt,FitnessGoal goal){
        this.id=id;
        this.status=RequestStatus.PENDING;
        this.clientEmail=client;
        this.ptEmail=pt;
        this.goal=goal;
    }

    public PlanRequest(int id,String client,String pt,FitnessGoal goal,RequestStatus status){
        this.id=id;
        this.status=status;
        this.clientEmail=client;
        this.ptEmail=pt;
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

    public String getPtEmail() {
        return ptEmail;
    }

    public void setPtEmail(String pt) {
        this.ptEmail = pt;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClient(String client) {
        this.clientEmail = client;
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
