package view;
import bean.PersonalTrainerBean;
import bean.PlanRequestBean;
import controller.ManageCustomPlanRequestController;
import exception.BusinessException;
import exception.ControllerException;
import exception.UnavailableServiceException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import model.FitnessGoal;
import java.util.List;


public class RequestPlanGraphicGraphicControllerGUI {
    @FXML
    private ComboBox<PersonalTrainerBean> trainerComboBox;
    @FXML
    private ToggleGroup fitnessGoalGroup;
    @FXML
    private Button sendRequestBtn;
    @FXML
    private Button cancelBtn;

    private Parent view;

    private Navigator navigator;
    private static final String ERROR_TEXT="Errore";

    public void setGUINavigator(Navigator n){
        this.navigator=n;
    }
    public void setView(Parent view){
        this.view=view;
    }
    public Parent getView(){
        return  this.view;
    }



    private void loadPersonalTrainers() {
        try {
            ManageCustomPlanRequestController ctrl=new ManageCustomPlanRequestController();
            List<PersonalTrainerBean> trainers=ctrl.retrievePT();

            trainerComboBox.setItems(
                FXCollections.observableArrayList(trainers)
            );

            // Custom cell factory per mostrare nome, cognome e email
            trainerComboBox.setCellFactory(param ->
                new ListCell<PersonalTrainerBean>() {
                    @Override
                    protected void updateItem(PersonalTrainerBean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getName() + " " + item.getSurname() +
                                   " (" + item.getEmail() + ")");
                        }
                    }
                }
            );

            // Mostra il valore selezionato nel bottone principale
            trainerComboBox.setButtonCell(new ListCell<PersonalTrainerBean>() {
                @Override
                protected void updateItem(PersonalTrainerBean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getName() + " " + item.getSurname());
                    }
                }
            });

        } catch (UnavailableServiceException | ControllerException e) {
            showAlert(ERROR_TEXT,"Recupero pt fallito","Impossibile recuperare i trainer disponibili,riprovare più tardi");
        }
    }

    @FXML
    private void handleSendRequest(ActionEvent event) {
        try {
            PersonalTrainerBean selectedTrainer =
                trainerComboBox.getSelectionModel().getSelectedItem();

            // Ottieni il FitnessGoal dal RadioButton selezionato
            Toggle selectedToggle = fitnessGoalGroup.getSelectedToggle();
            if (selectedToggle == null) {
                showAlert("Attenzione", ERROR_TEXT,
                    "Seleziona un obiettivo fitness");
                return;
            }

            RadioButton selectedRadio = (RadioButton) selectedToggle;
            String goalText = selectedRadio.getText();
            FitnessGoal selectedGoal = mapTextToFitnessGoal(goalText);

            if (selectedTrainer == null || selectedGoal == null) {
                showAlert("Attenzione", ERROR_TEXT,
                    "Seleziona sia un trainer che un obiettivo");
                return;
            }

            // Crea il bean (nota: usa email come stringhe)
            PlanRequestBean requestBean = new PlanRequestBean(
                navigator.getSession().getAthlete().getEmail(),
                selectedTrainer.getEmail(),
                selectedGoal
            );

            // Invia al controller applicativo
            ManageCustomPlanRequestController appController =
                new ManageCustomPlanRequestController();
            appController.sendPlanRequest(requestBean);

            // Mostra successo
            showAlert("Successo", "Richiesta inviata",
                "La tua richiesta è stata inviata a " +
                selectedTrainer.getName() + "!");

            // Ritorna al dashboard
            returnToDashboard();

        } catch (ControllerException  | UnavailableServiceException e) {
            showAlert(ERROR_TEXT, "Errore nell'invio",
                e.getMessage());
        }
        catch (BusinessException e){
            showAlert(ERROR_TEXT,"Business viiolation",e.getMessage());
        }
    }
    public void start(){
        loadPersonalTrainers();
        trainerComboBox.getSelectionModel().clearSelection();
        fitnessGoalGroup.selectToggle(null);
    }


    @FXML
    private void handleCancel(ActionEvent event) {
        returnToDashboard();
    }

    @FXML
    private void returnToDashboard() {
       this.navigator.goToAthleteDashboard();

    }

    private FitnessGoal mapTextToFitnessGoal(String text) {
        return switch(text) {
            case "Weight Loss" -> FitnessGoal.WEIGHT_LOSS;
            case "Improve Strength" -> FitnessGoal.STRENGHT;
            case "Body Recomposition" ->FitnessGoal.BODY_RECOMPOSITION;
            default -> null;
        };
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
