package model;

public class Athlete {
    private String email;
    private String nome;
    private String cognome;
    private PlanRequest request;
    private PersonalTrainer pt;
    private Gender gender;
    private int height;
    private double weight;

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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Athlete(String nome, String cognome, double peso, int altezza, Gender gender) {
        this.nome = nome;
        this.cognome = cognome;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.pt = null;
        this.request=null;
    }
}
