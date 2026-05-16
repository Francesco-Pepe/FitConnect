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
import javafx.stage.Stage;


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



    public void setAthlete(ActionEvent e){
        this.role=Role.ATHLETE;
        setLoginText("athlete");
        setStyle(btnAthlete,btnTrainer);
    }
    public void setPersonalTrainer(ActionEvent e){
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

    public SessionBean doLogin(){
        LoginController controller=new LoginController();
        String email=this.email.getText();
        String password=this.password.getText();
        if (email.length()==0 || password.length()==0){
            throw new InvalidCredentials("Every field should have a lenght >0");
        }
        if (this.role.equals(Role.ATHLETE)){
            AthleteBean athlete=new AthleteBean(email,password);
            SessionBean session =controller.logAsAthlete(athlete);
            return session;
        }
        else {
            PersonalTrainerBean pt=new PersonalTrainerBean(email,password);
            SessionBean session=controller.logAsPersonalTrainer(pt);
            return session;

        }

    }

    public void start() {
        email.clear();
        password.clear();
    }
}
