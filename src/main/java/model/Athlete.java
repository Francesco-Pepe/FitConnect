package model;

public class Athlete {
    private final String email;
    private final String name;
    private final String surname;
    private TrainingPlan plan;
    private PersonalTrainer pt;
    private Gender gender;
    private int height;
    private double weight;

    public Athlete(String email,String name, String surname, PhysicalTraits traits) {
        this(email,name,surname,traits,null,null);
    }

    //physicaltraits to bypass sonar smell for 8 parameter method
    public Athlete(String email,String name, String surname, PhysicalTraits traits,PersonalTrainer pt,TrainingPlan plan) {
        this.email=email;
        this.name = name;
        this.surname = surname;
        this.weight = traits.getWeight();
        this.height = traits.getHeight();
        this.gender = traits.getGender();
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


    public String getName() {
        return name;
    }


    public void assignPlan(PersonalTrainer pt,TrainingPlan plan){
        this.pt=pt;
        this.plan=plan;
    }


}
