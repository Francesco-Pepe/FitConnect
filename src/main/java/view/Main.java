package view;

import eng.NavigatorFactory;
import javafx.application.Platform;


public class Main  {

    public static void main(String[] args) {
        Platform.startup(() -> {
            Navigator navigator = NavigatorFactory.getNavigatorFactory().createNavigator();
            // Chiamo il navigatore senza sapere quale sto usando
            navigator.startUp();
        });
    }

}
