package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import static javafx.application.Application.launch;

public class Main  {

    public static void main(String[] args) {
        Platform.startup(() -> {
            Navigator navigator = new NavigatorGUI();
            // Chiamo il navigatore senza sapere quale sto usando
            navigator.startUp();
        });
    }

}
