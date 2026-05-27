package bean;

import bean.enums.Event;

import java.time.LocalDateTime;

public class NotificaBean {
    private String mittente;
    private String destinatario;
    private LocalDateTime momentoInvio;
    private Event evento;


    public NotificaBean(String mittente, String destinatario, LocalDateTime momentoInvio, Event evento) {
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.momentoInvio = momentoInvio;
        this.evento = evento;
    }

    public String getMittente() {
        return mittente;
    }
    public String getDestinatario() {
        return destinatario;
    }
    public LocalDateTime getMomentoInvio() {
        return momentoInvio;
    }
    public Event getEvent() {
        return evento;
    }


}

