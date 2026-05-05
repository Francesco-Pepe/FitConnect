package model;

public class Athlete {
    private String email;
    private String name;
    private String surname;
    private PlanRequest request;
    private PersonalTrainer pt;
    private Gender gender;
    private int height;
    private double weight;

    public String getEmail(){
        return this.email;
    }
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public PersonalTrainer getPt() {
        return pt;
    }

    public void setPt(PersonalTrainer pt) {
        this.pt = pt;
    }

    public PlanRequest getRequest() {
        return request;
    }

    public void setRequest(PlanRequest request) {
        this.request = request;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String cognome) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Athlete(String email,String name, String surname, double weight, int height, Gender gender) {
        this.email=email;
        this.name = name;
        this.surname = surname;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.pt = null;
        this.request=null;
    }
}
