package model;


public class Athlete {
    private String email;
    private String name;
    private String surname;
    private TrainingPlan plan;
    private PersonalTrainer pt;
    private Gender gender;
    private int height;
    private double weight;

    public Athlete(String email,String name, String surname, double weight, int height, Gender gender) {
        this(email,name,surname,weight,height,gender,null,null);
    }
    @SuppressWarnings("java:S107")//max number of parameters for a method
    public Athlete(String email,String name, String surname, double weight, int height, Gender gender,PersonalTrainer pt,TrainingPlan plan) {
        this.email=email;
        this.name = name;
        this.surname = surname;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.pt = pt;
        this.plan=plan;
    }

    public TrainingPlan getPlan() {
        return plan;
    }



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



    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void assignPlan(PersonalTrainer pt,TrainingPlan plan){
        this.pt=pt;
        this.plan=plan;
    }


}
