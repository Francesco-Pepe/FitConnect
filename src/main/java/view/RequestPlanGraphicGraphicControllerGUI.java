package view;
import bean.PersonalTrainerBean;
import bean.PlanRequestBean;
import controller.ManageCustomPlanController;

import exception.ControllerException;
import exception.DAOException;
import exception.UnavailableServiceException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.control.*;

import model.FitnessGoal;


import java.util.List;


public class RequestPlanGraphicGraphicControllerGUI
        {

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
            ManageCustomPlanController ctrl=new ManageCustomPlanController();
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
            ManageCustomPlanController appController =
                new ManageCustomPlanController();
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
    }
    public void start(){
        loadPersonalTrainers();
    }

    /**
     * Handler del pulsante "Cancel"
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        returnToDashboard();
    }

    /**
     * Ritorna alla dashboard dell'atleta
     */
    @FXML
    private void returnToDashboard() {
       this.navigator.goToAthleteDashboard();

    }

    /**
     * Mappa il testo del RadioButton al FitnessGoal enum
     */
    private FitnessGoal mapTextToFitnessGoal(String text) {
        return switch(text) {
            case "Weight Loss" -> FitnessGoal.WEIGHT_LOSS;
            case "Improve Strenght" -> FitnessGoal.STRENGHT;
            case "Body Recomposition" ->FitnessGoal.BODY_RECOMPOSITION;
            default -> null;
        };
    }

    /**
     * Helper per mostrare alert
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // ===== Implementazione metodi astratti (non usati nella GUI) =====

}
