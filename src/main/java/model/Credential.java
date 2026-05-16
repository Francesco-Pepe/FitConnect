package model;

public class Credential {
    String email;
    String hashPassword;
    public Credential(String email,String password){
        this.email=email;
        this.hashPassword=password;
    }

    public String getEmail() {
        return email;
    }

    public String getHashPassword() {
        return hashPassword;
    }
}
