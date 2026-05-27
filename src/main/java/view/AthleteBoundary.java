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

public class AthleteBoundary {
    private static final String NOTIFICATION_TITLE="ATHLETES'S BOUNDARY";
    private  static final String DATE_FORMAT="dd/MM/yyyy HH:mm";
    private  static final String HOUR_NOTIFY="Hour: ";
    public void sendNotification(NotificaBean n){
        try (InputStream in = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(in);

            String grafica = prop.getProperty("ui.type");

            if (grafica != null) {
                SupportedUI version = SupportedUI.valueOf(grafica.toUpperCase());
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
    public void sendRejection(NotificaBean n){
        try (InputStream in = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(in);

            String grafica = prop.getProperty("ui.type");

            if (grafica != null) {
                SupportedUI version = SupportedUI.valueOf(grafica.toUpperCase());
                if(version.equals(SupportedUI.GUI)){
                    this.showRejectPopUp(n);
                } else {
                    this.showRejection(n);
                }
            }
        } catch (IOException e) {
            showMessage(n);
        }
    }
    private void showRejection(NotificaBean n) {
        System.out.println(NOTIFICATION_TITLE);
        String messaggio = " The trainer: " + n.getMittente() + "\n";
        String messaggio2 = "";
        if(n.getEvent() == Event.PLAN_CREATED) {
            messaggio2 = "Plan rejected from " + n.getMittente() +"\n";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        messaggio2 += HOUR_NOTIFY+ n.getMomentoInvio().format(formatter);
        System.out.println(messaggio+messaggio2);
    }
    private void showRejectPopUp(NotificaBean n) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Icona "i" blu
        alert.setTitle(NOTIFICATION_TITLE);
        alert.setHeaderText(null);
        String messaggio = "To: " + n.getDestinatario() + "\n";
        String messaggio2 = "The trainer "+n.getMittente()+" has rejected the plan request";
        if(n.getEvent() == Event.PLAN_CREATED) {
            messaggio2 = "You can now send another request" + n.getDestinatario() + "\n";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        messaggio2 += HOUR_NOTIFY+ n.getMomentoInvio().format(formatter);
        alert.setContentText(messaggio+messaggio2);
        alert.showAndWait();
    }

    private void showMessage(NotificaBean n) {
        System.out.println(NOTIFICATION_TITLE);
        String messaggio = "Il trainer: " + n.getMittente() + "\n";
        String messaggio2 = "";
        if(n.getEvent() == Event.PLAN_CREATED) {
            messaggio2 = "Plan created from " + n.getMittente() +"\n";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        messaggio2 += HOUR_NOTIFY+ n.getMomentoInvio().format(formatter);
        System.out.println(messaggio+messaggio2);
    }
    private void showPopUp(NotificaBean n) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Icona "i" blu
        alert.setTitle(NOTIFICATION_TITLE);
        alert.setHeaderText(null);
        String messaggio = "To: " + n.getDestinatario() + "\n";
        String messaggio2 = "The trainer "+n.getMittente()+" has accepted the plan request";
        if(n.getEvent() == Event.PLAN_CREATED) {
            messaggio2 = "The trainer has created the plan for" + n.getDestinatario() + "\n";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        messaggio2 += HOUR_NOTIFY+ n.getMomentoInvio().format(formatter);
        alert.setContentText(messaggio+messaggio2);
        alert.showAndWait();
    }
}

