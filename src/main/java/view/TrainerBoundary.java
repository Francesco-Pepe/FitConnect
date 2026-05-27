package view;

import bean.enums.Event;
import bean.NotificaBean;
import eng.SupportedUI;
import javafx.scene.control.Alert;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class TrainerBoundary {
        public void sendNotification(NotificaBean n){
            try (InputStream in = new FileInputStream("config.properties")) {
                Properties prop = new Properties();
                prop.load(in);

                String graphics = prop.getProperty("ui.type");

                if (graphics != null) {
                    SupportedUI version = SupportedUI.valueOf(graphics.toUpperCase());
                    if(version.equals(SupportedUI.GUI)){
                        this.showPopUp(n);
                    } else {
                        this.showMessage(n);
                    }
                }
            } catch (IOException e) {
                showMessage(n);
            }
        }

        private void showMessage(NotificaBean n) {
            System.out.println("TRAINER'S BOUNDARY");
            String messaggio = "The athlete: " + n.getMittente() + "\n";
            String messaggio2 = "";
            if(n.getEvent() == Event.NEW_REQUEST) {
                messaggio2 = "New plan request from " + n.getMittente() +"\n";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            messaggio2 += "Ora: "+ n.getMomentoInvio().format(formatter);
            System.out.println(messaggio+messaggio2);
        }
        private void showPopUp(NotificaBean n) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // Icona "i" blu
            alert.setTitle("TRAINER'S BOUNDARY");
            alert.setHeaderText(null);
            String messaggio = "To: " + n.getDestinatario() + "\n";
            String messaggio2 = "";
            if(n.getEvent() == Event.NEW_REQUEST) {
                messaggio2 = "The athlete "+n.getMittente()+" has sent a plan request\n";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            messaggio2 += "Ora: "+ n.getMomentoInvio().format(formatter);
            alert.setContentText(messaggio+messaggio2);
            alert.showAndWait();
        }
    }
