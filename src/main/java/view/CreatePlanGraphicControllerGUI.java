package view;

import bean.ExerciseBean;
import bean.PlanRequestBean;
import bean.TrainingPlanBean;
import controller.ManageCustomPlanController;
import exception.ControllerException;
import exception.UnavailableServiceException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;
import java.util.List;

public class CreatePlanGraphicControllerGUI {
    private Navigator navigator;
    private Parent view;
    private static final String FONT_FAMILY="System";
    @FXML
    TextField athleteNameField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker expireDatePicker;
    @FXML
    VBox exercisesContainer;

    public void setGUINavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    public Parent getView() {
        return view;
    }
    public void setView(Parent view){
        this.view=view;
    }


    public void start(){
        setAthleteName();
        populateExercise(navigator.getExercises());
        startDatePicker.setValue(null);
        expireDatePicker.setValue(null);
    }


    @FXML
    private void backToDashboard(){
        navigator.goToTrainerDashboard();
    }
    @FXML
    private void addExercise(){
        navigator.goToAddExercise();
    }

    private void deleteExercise(ExerciseBean ex){
        navigator.deleteExercise(ex);
        exercisesContainer.getChildren().clear();
        List<ExerciseBean> exercises=navigator.getExercises();
        populateExercise(exercises);
    }

    public void populateExercise(List<ExerciseBean> exercises) {
        exercisesContainer.getChildren().clear();
        int exerciseNumber = 1;
        if (exercises.isEmpty()){
            return;
        }
        for (ExerciseBean ex : exercises) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPrefHeight(60.0);
            row.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 8; -fx-padding: 0 20;");
            StackPane circlePane = new StackPane();
            Circle circle = new Circle(12.0, Color.web("#6345ff"));
            Label numLabel = new Label(String.valueOf(exerciseNumber++));
            numLabel.setTextFill(Color.WHITE);
            numLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 11;");
            circlePane.getChildren().addAll(circle, numLabel);

            VBox textContainer = new VBox(2.0);
            textContainer.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(textContainer, Priority.ALWAYS); // Spinge il cestino a destra
            HBox.setMargin(textContainer, new Insets(0, 0, 0, 15));

            Label titleLabel = new Label(ex.getExerciseName());
            titleLabel.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 14));
            titleLabel.setTextFill(Color.web("#1e293b"));

            Label detailsLabel = new Label(ex.getSets()+" sets x "+ex.getReps()+" reps");
            detailsLabel.setFont(Font.font(FONT_FAMILY, 12));
            detailsLabel.setTextFill(Color.web("#64748b"));

            textContainer.getChildren().addAll(titleLabel, detailsLabel);
            Button deleteBtn = new Button("X");
            deleteBtn.setFont(Font.font(FONT_FAMILY, 16.0));
            deleteBtn.setTextFill(Color.web("#ef4444"));
            deleteBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

            deleteBtn.setOnAction(event ->
                deleteExercise(ex)
            );
            row.getChildren().addAll(circlePane, textContainer, deleteBtn);
            exercisesContainer.getChildren().add(row);
        }
    }

    private void setAthleteName(){
        PlanRequestBean req =navigator.getPlanRequest();
        athleteNameField.setText(req.getAthlete());
        athleteNameField.setEditable(false);
    }

    private boolean verifyDate(){
        if (startDatePicker.getValue()==null || expireDatePicker.getValue()==null){
            return false;
        }
        return !startDatePicker.getValue().isAfter(expireDatePicker.getValue()) && !startDatePicker.getValue().isBefore(LocalDate.now());
    }
    @FXML
    public void createPlan()  {
        List<ExerciseBean> exercises=navigator.getExercises();
        try {


            if (verifyDate() && !exercises.isEmpty()) {
                ManageCustomPlanController controller = new ManageCustomPlanController();
                TrainingPlanBean plan = new TrainingPlanBean(startDatePicker.getValue(), expireDatePicker.getValue(), navigator.getExercises());
                PlanRequestBean req = navigator.getPlanRequest();
                controller.acceptAndCreatePlan(req, plan);
                showAlert("Success", "Plan created", "The training plan has been succesfully created!");
                navigator.goToTrainerDashboard();
            } else {
                showAlert("Errore", "Data not valid", "The fields cannot be empty");
            }
        }catch (ControllerException  | UnavailableServiceException e){
            showAlert("Error","Plan creation has failed","Try later");
        }

    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
