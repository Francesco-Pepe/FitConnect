package eng;
import view.Navigator;
import view.NavigatorCLI;
import view.NavigatorGUI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class NavigatorFactory {

    private static NavigatorFactory instance = null;

    private NavigatorFactory(){}

    public static synchronized NavigatorFactory getNavigatorFactory() {
        if (instance == null) {
            instance = new NavigatorFactory();
        }
        return instance;
    }

    public Navigator createNavigator() {

        Navigator toRet = new NavigatorGUI();

        try (InputStream in = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(in);

            String graphics = prop.getProperty("ui.type");

            if (graphics != null) {
                SupportedUI version = SupportedUI.valueOf(graphics.toUpperCase());
                if(version.equals(SupportedUI.GUI)){
                    toRet = new NavigatorGUI();
                } else {
                    toRet = new NavigatorCLI();
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            // Ignoro
        }
        return toRet;
    }
}