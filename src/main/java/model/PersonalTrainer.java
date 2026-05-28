package model;

public class PersonalTrainer {
    private final String email;
    private final String name;
    private final String surname;
    private final Gender gender;

    public PersonalTrainer(String email,String name,String surname,Gender gender){
        this.email=email;
        this.name=name;
        this.surname=surname;
        this.gender=gender;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }


    public Gender getGender() {
        return this.gender;
    }
}
