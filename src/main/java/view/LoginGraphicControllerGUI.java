package view;

import bean.AthleteBean;
import bean.PersonalTrainerBean;
import bean.Role;
import bean.SessionBean;
import controller.LoginController;
import exception.InvalidCredentials;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginGraphicControllerGUI {
    @FXML
    private Button btnAthlete;
    private Role role=Role.ATHLETE;
    @FXML
    private Button loginButton;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button btnTrainer;
    private Parent view;
    private Navigator navigator;

    public void setView(Parent view){
        this.view=view;
    }
    public Parent getView(){
        return this.view;
    }

    public void setGUINavigator(Navigator n){
        this.navigator=n;
    }



    public void setAthlete(){
        this.role=Role.ATHLETE;
        setLoginText("athlete");
        setStyle(btnAthlete,btnTrainer);
    }
    public void setPersonalTrainer(){
        this.role=Role.PERSONAL_TRAINER;
        setLoginText("personal trainer");
        setStyle(btnTrainer,btnAthlete);
    }
    private void setLoginText(String role){
        loginButton.setText("Login as "+role);
    }

    private void setStyle(Button active,Button inactive){
        active.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-text-fill: #6345ff; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2)");
        inactive.setStyle("-fx-background-color: transparent; -fx-text-fill: #6b7280");
    }

    public void doLogin(){
        LoginController controller=new LoginController();
        String userMail=this.email.getText();
        String userPassword=this.password.getText();
        if (userMail.isEmpty() || userPassword.isEmpty()){
            throw new InvalidCredentials("Every field should have a lenght >0");
        }
        if (this.role.equals(Role.ATHLETE)){
            AthleteBean athlete=new AthleteBean(userMail,userPassword);
            SessionBean session =controller.logAsAthlete(athlete);
            navigator.setSession(session);
            navigator.setAthlete(session.getAthlete());
            navigator.goToAthleteDashboard();
        }
        else {
            PersonalTrainerBean pt=new PersonalTrainerBean(userMail,userPassword);
            SessionBean session=controller.logAsPersonalTrainer(pt);
            navigator.setSession(session);
            navigator.setPt(session.getPt());
            navigator.goToTrainerDashboard();

        }

    }

    public void start() {
        email.clear();
        password.clear();
    }
}
