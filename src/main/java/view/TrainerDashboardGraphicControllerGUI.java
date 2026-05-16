package view;

import bean.PersonalTrainerBean;
import bean.PlanRequestBean;
import controller.ManageCustomPlanController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.List;

public class TrainerDashboardGraphicControllerGUI {
    private Parent view;
    private Navigator navigator;
    @FXML
    VBox requestsContainer;
    @FXML
    Label trainerName;

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
            nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
            nameLabel.setTextFill(Color.web("#1e293b"));


            headerBox.getChildren().addAll(nameLabel);

            // 3. Descrizione/Obiettivo del cliente
            Label goalLabel = new Label(req.getGoal().toString());
            goalLabel.setFont(Font.font("System", 14));
            goalLabel.setTextFill(Color.web("#475569"));
            goalLabel.setWrapText(true); // Permette al testo di andare a capo se lungo
            VBox.setMargin(goalLabel, new Insets(10, 0, 15, 0));

            // 4. Box dei Pulsanti (Accept & Decline)
            HBox buttonsBox = new HBox(12);

            // Pulsante verde "Accept & Create Plan"
            Button acceptBtn = new Button("✓ Accept & Create Plan");
            acceptBtn.setFont(Font.font("System", FontWeight.BOLD, 13));
            acceptBtn.setTextFill(Color.WHITE);
            acceptBtn.setStyle("-fx-background-color: #00ba54; -fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 8 16;");

            // Gestione del click di accettazione per QUESTO specifico cliente
            acceptBtn.setOnAction(event -> handleAcceptAction(req));

            // Pulsante bianco/rosso "Decline"
            Button declineBtn = new Button("✕ Decline");
            declineBtn.setFont(Font.font("System", FontWeight.BOLD, 13));
            declineBtn.setTextFill(Color.web("#ef4444"));
            declineBtn.setStyle("-fx-background-color: white; -fx-border-color: #fca5a5; -fx-border-radius: 6; -fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 8 16;");

            // Gestione del click di rifiuto per QUESTO specifico cliente
            declineBtn.setOnAction(event -> {
                try {
                    handleDeclineAction(req);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            buttonsBox.getChildren().addAll(acceptBtn, declineBtn);

            // 5. Assemblaggio finale nel VBox della richiesta
            requestBox.getChildren().addAll(headerBox, goalLabel, buttonsBox);

            // 6. Iniezione nel contenitore principale della pagina
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
    public void start() throws IOException {
        PersonalTrainerBean pt=navigator.getPt();
        String trainer= pt.getName()+" "+pt.getSurname();
        setTrainerName(trainer);
        ManageCustomPlanController ctrl=new ManageCustomPlanController();
        List<PlanRequestBean> requests=ctrl.getPendingRequests(pt.getEmail());
        populateRequests(requests);
    }
    @FXML
    private void handleDeclineAction(PlanRequestBean req) throws IOException {
        ManageCustomPlanController ctrl=new ManageCustomPlanController();
        ctrl.declineRequest(req);

    }
    @FXML
    private void handleAcceptAction(PlanRequestBean req) throws IOException {
        this.navigator.setPlanRequest(req);
        navigator.goToCreatePlan();
    }


}
