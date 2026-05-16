package bean;

public class PersonalTrainerBean {
    String name;
    String surname;
    String email;
    String password;

    public PersonalTrainerBean(String email,String password){
        this.email=email;
        this.password=password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public void clearPassword(){
        this.password="";
    }
}
