package view;
import bean.PersonalTrainerBean;
import bean.PlanRequestBean;
import controller.ManageCustomPlanController;
import exception.ControllerException;
import exception.UnavailableServiceException;
import model.FitnessGoal;
import java.util.List;
import java.util.Scanner;

public class RequestPlanGraphicControllerCLI {

    private Navigator navigator;

    public void setNavigator(Navigator n) {
        this.navigator = n;
    }

    public void start(Scanner sc)  {
        printHeader("RICHIEDI PIANO");
        ManageCustomPlanController ctrl;
        // Carica lista PT
        try {
             ctrl = new ManageCustomPlanController();
        }catch (UnavailableServiceException e){
            System.out.println("Impossibile comunicare con il database degli esercizi,riprovare più tardi");
            navigator.goToAthleteDashboard();
            return;
        }
        List<PersonalTrainerBean> trainers;
        try {
            trainers = ctrl.retrievePT();
        } catch (ControllerException e) {
            System.out.println("[!] Impossibile caricare i personal trainer: ");
            navigator.goToAthleteDashboard();
            return;
        }

        if (trainers.isEmpty()) {
            handleNoTrainers(sc);
            return;
        }
        printTrainers(trainers);
        PersonalTrainerBean selectedTrainer = null;
        while (selectedTrainer == null) {
            String input = sc.nextLine().trim();
            if ("0".equals(input)) {
                navigator.goToAthleteDashboard();
                return;
            }
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < trainers.size()) {
                    selectedTrainer = trainers.get(idx);
                } else {
                    System.out.print("[!] Numero fuori range. Riprova: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("[!] Inserisci un numero valido: ");
            }
        }

        // Selezione obiettivo
        System.out.println("\n  Obiettivo fitness:");
        FitnessGoal[] goals = FitnessGoal.values();
        for (int i = 0; i < goals.length; i++) {
            System.out.printf("    [%d] %s%n", i + 1, goals[i]);
        }
        System.out.print("\n> Scelta obiettivo: ");

        FitnessGoal selectedGoal = null;
        while (selectedGoal == null) {
            String input = sc.nextLine().trim();
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < goals.length) {
                    selectedGoal = goals[idx];
                } else {
                    System.out.print("[!] Numero fuori range. Riprova: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("[!] Inserisci un numero valido: ");
            }
        }

        // Invio richiesta
        try {
            PlanRequestBean request = new PlanRequestBean(
                    navigator.getSession().getAthlete().getEmail(),
                    selectedTrainer.getEmail(),
                    selectedGoal
            );
            ctrl.sendPlanRequest(request);
            System.out.printf("%n  Richiesta inviata a %s %s!%n",
                    selectedTrainer.getName(), selectedTrainer.getSurname());
        } catch (ControllerException e) {
            System.out.println(" Errore invio richiesta: " + e.getMessage());
        }
        System.out.print("  Premi INVIO per tornare alla dashboard... ");
        sc.nextLine();
        navigator.goToAthleteDashboard();
    }

    private static void printHeader(String title) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.printf( "║  %-28s║%n", "FitConnect — " + title);
        System.out.println("╚══════════════════════════════╝");
    }

    private void handleNoTrainers(Scanner sc){
        System.out.println("  Nessun personal trainer disponibile.");
        System.out.print("  Premi INVIO per tornare... ");
        sc.nextLine();
        navigator.goToAthleteDashboard();
    }

    private void printTrainers(List<PersonalTrainerBean> pts){
        System.out.println("  Personal Trainer disponibili:");
        for (int i = 0; i < pts.size(); i++) {
            PersonalTrainerBean pt = pts.get(i);
            System.out.printf("    [%d] %s %s (%s)%n", i + 1, pt.getName(), pt.getSurname(), pt.getEmail());
        }
        System.out.println("    [0] Annulla");
        System.out.print("\n> Scelta trainer: ");
    }

}

