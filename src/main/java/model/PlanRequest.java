package model;

public class PlanRequest {
    private int id;
    private RequestStatus status;
    private final String clientEmail;
    private final String ptEmail;
    private final FitnessGoal goal;

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



    public String getPtEmail() {
        return ptEmail;
    }


    public String getClientEmail() {
        return clientEmail;
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
