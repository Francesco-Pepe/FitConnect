package view;

import bean.AthleteBean;
import bean.SessionBean;
import bean.TrainingPlanBean;
import controller.ManageCustomPlanController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Athlete;
import model.Gender;

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

    public AthleteDashboardGraphicControllerGUI() {

    }


    public void setAthleteName(String athleteName) {
        this.athleteName.setText("Benvenuto " + athleteName);
    }

    public void requestPlan(ActionEvent e) throws IOException {
        System.out.println("DEBUG: requestPlan() called");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RequestPlan.fxml"));
            System.out.println("DEBUG: FXMLLoader created");

            VBox root = loader.load();
            System.out.println("DEBUG: FXML loaded");

            RequestPlanGraphicGraphicControllerGUI planController = loader.getController();
            System.out.println("DEBUG: Controller retrieved: " + planController);

            // Crea un atleta di test per il prototipo
            // In produzione, questo dovrebbe venire dal sistema di login
            Athlete testAthlete = new Athlete(
                    "francesco@test.com",
                    "Francesco",
                    "Rossi",
                    75.5,
                    180,
                    Gender.MALE
            );
            System.out.println("DEBUG: Test athlete created");

            // Passa l'atleta e lo stage al controller della richiesta

            System.out.println("DEBUG: Scene displayed");
        } catch (Exception ex) {
            System.out.println("ERROR in requestPlan: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public void viewPlan(ActionEvent e) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/ViewPlan.fxml"));
        javafx.scene.layout.AnchorPane root=loader.load();
        ViewPlanControllerGUI planControllerGUI=loader.getController();

    }
    public void logout(ActionEvent e) throws IOException {
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
