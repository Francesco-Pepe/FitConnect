package view;

import javafx.application.Platform;


public class Main  {

    public static void main(String[] args) {
        Platform.startup(() -> {
            Navigator navigator = new NavigatorGUI();
            // Chiamo il navigatore senza sapere quale sto usando
            navigator.startUp();
        });
    }

}
