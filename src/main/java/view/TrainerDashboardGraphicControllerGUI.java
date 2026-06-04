package view;

import bean.PersonalTrainerBean;
import bean.PlanRequestBean;
import controller.ManageCustomPlanRequestController;
import exception.ControllerException;
import exception.UnavailableServiceException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import java.util.List;

public class TrainerDashboardGraphicControllerGUI {
    private Parent view;
    private Navigator navigator;
    @FXML
    VBox requestsContainer;
    @FXML
    Label trainerName;
    private static final String FONT_FAMILY="System";
    private static final String ERROR_TITLE="Error";

    private void populateRequests(List<PlanRequestBean> requests) {
        requestsContainer.getChildren().clear();
        for (int i = 0; i < requests.size(); i++) {
            PlanRequestBean req = requests.get(i);

            VBox requestBox = new VBox();
            requestBox.setPadding(new Insets(25));

            if (i < requests.size() - 1) {
                requestBox.setStyle("-fx-border-color: #f1f5f9; -fx-border-width: 0 0 1 0;");
            }

            HBox headerBox = new HBox(10);
            headerBox.setAlignment(Pos.BASELINE_LEFT);

            Label nameLabel = new Label(req.getAthlete());
            nameLabel.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 16));
            nameLabel.setTextFill(Color.web("#1e293b"));

            headerBox.getChildren().addAll(nameLabel);

            Label goalLabel = new Label(req.getGoal().toString());
            goalLabel.setFont(Font.font(FONT_FAMILY, 14));
            goalLabel.setTextFill(Color.web("#475569"));
            goalLabel.setWrapText(true);
            VBox.setMargin(goalLabel, new Insets(10, 0, 15, 0));

            HBox buttonsBox = new HBox(12);

            Button acceptBtn = new Button("✓ Accept & Create Plan");
            acceptBtn.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 13));
            acceptBtn.setTextFill(Color.WHITE);
            acceptBtn.setStyle("-fx-background-color: #00ba54; -fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 8 16;");
            acceptBtn.setOnAction(event -> handleAcceptAction(req));

            Button declineBtn = new Button("✕ Decline");
            declineBtn.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 13));
            declineBtn.setTextFill(Color.web("#ef4444"));
            declineBtn.setStyle("-fx-background-color: white; -fx-border-color: #fca5a5; -fx-border-radius: 6; -fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 8 16;");

            // Passa il riferimento al proprio requestBox per poterlo rimuovere dalla UI
            declineBtn.setOnAction(event -> handleDeclineAction(req, requestBox));

            buttonsBox.getChildren().addAll(acceptBtn, declineBtn);
            requestBox.getChildren().addAll(headerBox, goalLabel, buttonsBox);
            requestsContainer.getChildren().add(requestBox);
        }
    }

    private void setTrainerName(String name){
        trainerName.setText("Welcome trainer! "+name);
    }

    public void setView(Parent view){
        this.view=view;
    }
    public Parent getView(){
        return this.view;
    }
    public void setGUINavigator(Navigator n){
        this.navigator=n;
    }

    public void start()  {
        PersonalTrainerBean pt = navigator.getPt();
        navigator.setExercises(new ArrayList<>());
        String trainer = pt.getName()+" "+pt.getSurname();
        setTrainerName(trainer);
        try {
            ManageCustomPlanRequestController ctrl = new ManageCustomPlanRequestController();
            List<PlanRequestBean> requests = ctrl.getPendingRequests(pt.getEmail());
            populateRequests(requests);
        }catch (UnavailableServiceException e){
            showAlert(ERROR_TITLE,"","System currently unavailable,retry later");
        }
        catch (ControllerException d){
            showAlert(ERROR_TITLE,"Error loading the requests",d.getMessage());
        }
    }

    public void logout() {
        try {
            ManageCustomPlanRequestController controller = new ManageCustomPlanRequestController();
            controller.logout(navigator.getSession().getId());
            // FIX: naviga al login dopo aver invalidato la sessione
            navigator.goToLogin();
        } catch (UnavailableServiceException e) {
            showAlert(ERROR_TITLE,"","Logout error");
        }
    }

    private void handleDeclineAction(PlanRequestBean req, VBox requestBox) {
        try {
            ManageCustomPlanRequestController ctrl = new ManageCustomPlanRequestController();
            ctrl.declineRequest(req);
            // FIX: nasconde la card senza toccare il resto del container
           requestsContainer.getChildren().remove(requestBox);
        } catch (ControllerException | UnavailableServiceException e) {
            showAlert(ERROR_TITLE,"","Request not rejected correctly,retry");
        }
    }

    @FXML
    private void handleAcceptAction(PlanRequestBean req) {
        this.navigator.setPlanRequest(req);
        navigator.goToCreatePlan();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}