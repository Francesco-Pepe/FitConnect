package view;

import bean.AthleteBean;
import bean.SessionBean;
import bean.TrainingPlanBean;
import controller.ManageCustomPlanController;
import exception.ControllerException;
import exception.UnavailableServiceException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class AthleteDashboardGraphicControllerGUI {
    @FXML
    Label athleteName = new Label("trone");
    @FXML
    Button logoutButton;
    @FXML
    Button requestButton;
    @FXML
    Button viewPlanButton;
    @FXML
    VBox trainingCard;
    @FXML
    Label creationDate;
    @FXML
    Label expirationDate;
    @FXML
    Label ptLabel;
    private Parent view;
    private Navigator navigator;

    public void setGUINavigator(NavigatorGUI navigator){
        this.navigator=navigator;
    }

    public void setView(Parent view){
        this.view=view;
    }

    public Parent getView(){
        return this.view;
    }

    public void setAthleteName(String athleteName) {
        this.athleteName.setText("Benvenuto " + athleteName);
    }

    public void requestPlan()  {
        navigator.goToPlanRequest();
    }
    public void viewPlan()  {
        navigator.goToViewPLan();

    }
    public void logout()  {
        int id=navigator.getSession().getId();
        try {
            ManageCustomPlanController ctrl = new ManageCustomPlanController();
            ctrl.logout(id);
            navigator.goToLogin();
        }catch (UnavailableServiceException e){
            showAlert("Error","",e.getMessage());
        }
    }

    public void start()  {
        try {
            ManageCustomPlanController controller = new ManageCustomPlanController();
            SessionBean session = navigator.getSession();
            AthleteBean athlete = session.getAthlete();
            setAthleteName(athlete.getName());
            String pt = athlete.getTrainer();
            if (pt != null) {
                TrainingPlanBean plan = controller.getAthletePlan(athlete.getEmail());
                navigator.setPlan(plan);
                showTrainingCard(plan, pt);
            }
        }catch (UnavailableServiceException e){
            showAlert("Errore","ExerciseError","Il servizio non è attualmente disponibile si prega di riprovare più tardi");
        }
        catch (ControllerException d){
            showAlert("Error","",d.getMessage());
        }

    }

    private void showTrainingCard(TrainingPlanBean plan,String pt){
        creationDate.setText(plan.getCreation().toString());
        expirationDate.setText(plan.getExpiration().toString());
        ptLabel.setText(pt);
        navigator.setTrainerName(pt);
        trainingCard.setVisible(true);
        trainingCard.setManaged(true);
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
