package model;

import java.util.ArrayList;
import java.util.List;

public class PersonalTrainer {
    private String email;
    private String name;
    private String surname;
    private List<PlanRequest> requests;
    private List<Athlete> clients;


    public PersonalTrainer(String email,String name,String surname){
        this.email=email;
        this.name=name;
        this.surname=surname;
        this.requests=new ArrayList<>();
        this.clients=new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<PlanRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<PlanRequest> requests) {
        this.requests = requests;
    }

    public List<Athlete> getClients() {
        return clients;
    }

    public void setClients(List<Athlete> clients) {
        this.clients = clients;
    }

    public void addRequest(PlanRequest req){
        this.requests.add(req);
    }
    public void addAthlete(Athlete client){
        this.clients.add(client);
    }
}
