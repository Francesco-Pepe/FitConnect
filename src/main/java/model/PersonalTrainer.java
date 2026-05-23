package model;



public class PersonalTrainer {
    private String email;
    private String name;
    private String surname;
    private Gender gender;


    public PersonalTrainer(String email,String name,String surname,Gender gender){
        this.email=email;
        this.name=name;
        this.surname=surname;
        this.gender=gender;
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


    public Gender getGender() {
        return this.gender;
    }
}
