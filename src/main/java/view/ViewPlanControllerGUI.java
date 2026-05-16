package view;

import bean.ExerciseBean;

import bean.TrainingPlanBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.geometry.Pos;
import javafx.scene.Parent;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;


import java.io.IOException;
import java.util.List;

public class ViewPlanControllerGUI {
    private Parent view;
    private Navigator navigator;
    @FXML
    Label trainerLabel;
    @FXML
    Label creationLabel;
    @FXML
    Label expirationLabel;
    @FXML
    Label exerciseNumber;
    @FXML
    VBox exerciseContainer;
    public void setView(Parent view){
        this.view=view;
    }
    public void setGUINavigator(Navigator n){
        this.navigator=n;
    }
    public Parent getView(){
        return this.view;
    }

    public void start(){
        TrainingPlanBean plan=navigator.getPlan();
        trainerLabel.setText(navigator.getTrainerName());
        creationLabel.setText(plan.getCreation().toString());
        expirationLabel.setText(plan.getExpiration().toString());
        populateExerciseList(plan.getExercises());
        exerciseNumber.setText("All exercises (" + plan.getExercises().size() +" )");

    }
    private void populateExerciseList(List<ExerciseBean> exercises) {
        // Puliamo il contenitore per sicurezza
        exerciseContainer.getChildren().clear();

        int counter = 1;
        for (ExerciseBean  ex : exercises) {

            // Creazione del Box riga (HBox)
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPrefHeight(55.0);
            row.setStyle("-fx-background-color: #f9fafb; -fx-background-radius: 8; -fx-padding: 0 15;");

            // Il cerchio viola col numero
            Circle circle = new Circle(12.0, Color.web("#6345ff"));

            Label numLabel = new Label(String.valueOf(counter));
            numLabel.setTextFill(Color.WHITE);
            numLabel.setStyle("-fx-translate-x: -16; -fx-font-weight: bold;");

            // Testo dell'esercizio (es: "Bench Press - 4 sets x 8 reps")
            String exText = ex.getExerciseName() + " - " + ex.getSets() + " sets x " + ex.getReps();
            Label detailsLabel = new Label(exText);
            detailsLabel.setTextFill(Color.web("#333333"));
            detailsLabel.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(detailsLabel, Priority.ALWAYS); // Spinge il bottone info a destra

            // Bottone Info (Sostituiamo la Label statica ⓘ con un vero bottone invisibile/stilizzato)
            Button infoBtn = new Button("ⓘ");
            infoBtn.setTextFill(Color.web("#6345ff"));
            infoBtn.setFont(new Font(18));
            infoBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 0;");

            // AZIONE DEL BOTTONE: Mostra le tecniche speciali al clic
            infoBtn.setOnAction(event -> showTechniqueDetails(ex)
            );

            // Assembla i componenti nell'HBox
            row.getChildren().addAll(circle, numLabel, detailsLabel, infoBtn);

            // Aggiunge la riga appena creata al contenitore principale della pagina
            exerciseContainer.getChildren().add(row);

            counter++;
        }
    }
    public void backToDashboard() {
        navigator.goToAthleteDashboard();
    }
    private void showTechniqueDetails(ExerciseBean ex) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exercise details");
        // Se non ci sono tecniche inserite, mostra un messaggio standard
        String info =ex.getExecutionDetails();
        if (info == null || info.isEmpty()) {
            info = "Nessuna tecnica speciale prevista per questo esercizio.";
        }

        alert.setContentText(info);
        alert.showAndWait();
    }




    }



