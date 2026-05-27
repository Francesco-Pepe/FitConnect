package view;

import bean.ExerciseBean;
import bean.Enum.Technique;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AddExerciseGraphicControllerGUI {
    private Parent view;
    private Navigator navigator;

    @FXML
    TextField exerciseNameField;
    @FXML
    TextField repsField;
    @FXML
    TextField setsField;
    @FXML
    CheckBox dropsetCheck;
    @FXML
    CheckBox isometricCheck;
    @FXML
    CheckBox restPauseCheck;
    @FXML
    CheckBox forcedRepsCheck;
    @FXML
    CheckBox slowEccentricCheck;

    public Parent getView(){
        return this.view;
    }
    public void setView(Parent view){
        this.view=view;
    }
    public void setGUINavigator(Navigator n){
        this.navigator=n;
    }
    public void start(){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            // ^ = inizio stringa, \\d{0,2} = da 0 a 2 cifre numeriche, $ = fine stringa
            if (newText.matches("^\\d{0,2}$")) {
                return change;
            }
            return null; // Rifiuta se supera le 2 cifre o se inserisce lettere
        };
        TextFormatter<String> reps=new TextFormatter<>(filter);
        TextFormatter<String> sets=new TextFormatter<>(filter);
        exerciseNameField.clear();
        repsField.clear();
        setsField.clear();
        repsField.setTextFormatter(reps);
        setsField.setTextFormatter(sets);
    }

    private List<Technique> getTechniques(){
        List<Technique> techniques=new ArrayList<>();
        if (dropsetCheck.isSelected()){ techniques.add(Technique.DROP_SET);}
        if (restPauseCheck.isSelected()){ techniques.add(Technique.REST_PAUSE);}
        if (isometricCheck.isSelected()){ techniques.add(Technique.ISOMETRIC_PAUSE);}
        if (forcedRepsCheck.isSelected()){ techniques.add(Technique.FORCED_REPS);}
        if (slowEccentricCheck.isSelected()){ techniques.add(Technique.SLOW_ECCENTRIC);}
        return  techniques;
    }
    @FXML
    private void addExercise(){
        String name=exerciseNameField.getText();
        if (name.isEmpty()){
            showAlert("Error","Name field not valid","The name field has no text");
            return;
        }
        String repString=repsField.getText();
        String setString=setsField.getText();
        if (repString.isEmpty() || setString.isEmpty()){
            showAlert("Error","Field has no text","Check that reps and sets field are not empty");
            return;
        }
        List<Technique> tecniques=getTechniques();
        int reps=Integer.parseInt(repString);
        int sets=Integer.parseInt(setString);
        ExerciseBean newExercise=new ExerciseBean(name,sets,reps,tecniques);
        navigator.addExercise(newExercise);
        dropsetCheck.setSelected(false);
        restPauseCheck.setSelected(false);
        isometricCheck.setSelected(false);
        forcedRepsCheck.setSelected(false);
        slowEccentricCheck.setSelected(false);
        navigator.goToCreatePlan();
    }


    @FXML
    private void backToPlan(){
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
