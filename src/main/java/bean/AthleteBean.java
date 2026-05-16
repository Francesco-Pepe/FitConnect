package bean;


public class AthleteBean {
    private String name;
    private String surname;
    private String trainer;
    private String email;
    private String password;
    public AthleteBean(String email,String password){
        this.email=email;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    public void clearPassword(){
        this.password="";
    }


}
