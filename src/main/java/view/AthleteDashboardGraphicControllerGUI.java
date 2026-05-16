package view;

import bean.AthleteBean;
import bean.SessionBean;
import bean.TrainingPlanBean;
import controller.ManageCustomPlanController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


import java.io.IOException;

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
    public void logout() throws IOException {
        int id=navigator.getSession().getId();
        ManageCustomPlanController ctrl=new ManageCustomPlanController();
        ctrl.logout(id);

    }

    public void start() throws IOException {
        ManageCustomPlanController controller=new ManageCustomPlanController();
        SessionBean session=navigator.getSession();
        AthleteBean athlete=session.getAthlete();
        setAthleteName(athlete.getName());
        String pt=athlete.getTrainer();
        if (pt!=null){
            TrainingPlanBean plan=controller.getAthletePlan(athlete.getEmail());
            navigator.setPlan(plan);
            showTrainingCard(plan,pt);
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


}
