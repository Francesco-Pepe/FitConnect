package view;

import bean.ExerciseBean;
import bean.PlanRequestBean;
import bean.TrainingPlanBean;
import controller.ManageCustomPlanRequestController;
import exception.ControllerException;
import exception.InvalidExerciseException;
import exception.UnavailableServiceException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class CreatePlanGraphicControllerCLI {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Navigator navigator;

    public void setNavigator(Navigator n) {
        this.navigator = n;
    }

    public void start(Scanner sc) {
        PlanRequestBean req = navigator.getPlanRequest();
        printHeader("CREA PIANO");
        System.out.printf("  Atleta: %s%n",
                req.getAthlete() != null ? req.getAthlete() : req.getAthleteEmail());
        System.out.printf("  Obiettivo: %s%n", req.getGoal());

        while (true) {
            printExerciseTable(navigator.getExercises());
            printMenu();
            boolean done=false;
            switch (sc.nextLine().trim()) {
                case "1" -> navigator.goToAddExercise();
                case "2"  ->done=confirmAndSave(sc);
                case "3" ->removeExercise(sc);
                case "0" -> { navigator.goToTrainerDashboard(); return; }
                default  -> System.out.println(" Scelta non valida.\n");
            }
            if (done)
                return;
        }
    }

    private boolean confirmAndSave(Scanner sc) {
        if (!savePlan(sc)) return false;
        System.out.println("\n  Piano creato con successo!");
        System.out.print("  Premi INVIO per tornare alla dashboard... ");
        sc.nextLine();
        navigator.goToTrainerDashboard();
        return true;
    }

    private boolean savePlan(Scanner sc) {
        List<ExerciseBean> exercises = navigator.getExercises();
        if (exercises.isEmpty()) {
            System.out.println(" Aggiungi almeno un esercizio prima di salvare.\n");
            return false;
        }
        LocalDate startDate = readDate(sc, "  Data inizio (dd/MM/yyyy): ", LocalDate.now(ZoneId.systemDefault()), null);
        if (startDate == null) return false;
        LocalDate endDate = readDate(sc, "  Data scadenza (dd/MM/yyyy): ", startDate.plusDays(1), null);
        if (endDate == null) return false;
        try {
            ManageCustomPlanRequestController ctrl = new ManageCustomPlanRequestController();
            ctrl.acceptAndCreatePlan(navigator.getPlanRequest(), new TrainingPlanBean(startDate, endDate, exercises));
            return true;
        } catch (ControllerException | UnavailableServiceException | InvalidExerciseException e) {
            System.out.println(" Errore salvataggio piano: " + e.getMessage());
            return false;
        }

    }

    private LocalDate readDate(Scanner sc, String prompt, LocalDate minDate, LocalDate maxDate) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.equals("0")) return null;
            try {
                LocalDate date = LocalDate.parse(input, FMT);
                boolean tooEarly = minDate != null && date.isBefore(minDate);
                boolean tooLate  = maxDate != null && date.isAfter(maxDate);
                if (!tooEarly && !tooLate) return date;
                System.out.print(tooEarly
                        ? " La data deve essere >= " + minDate.format(FMT) + ". Riprova: "
                        : " La data deve essere <= " + maxDate.format(FMT) + ". Riprova: ");
            } catch (DateTimeParseException e) {
                System.out.print(" Formato non valido. Usa dd/MM/yyyy: ");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("  [1] Aggiungi esercizio");
        System.out.println("  [2] Salva piano");
        System.out.println("  [3] Rimuovi esercizio");
        System.out.println("  [0] Annulla e torna alla dashboard");
        System.out.print("\n> Scelta: ");
    }

    private void printExerciseTable(List<ExerciseBean> exercises) {
        if (exercises.isEmpty()) {
            System.out.println("\n  Nessun esercizio aggiunto.");
            return;
        }
        System.out.printf("%n  Esercizi aggiunti (%d):%n", exercises.size());
        System.out.println("  ┌────┬──────────────────────────┬───────────┐");
        System.out.println("  │ N° │ Esercizio                │ Set x Rep │");
        System.out.println("  ├────┼──────────────────────────┼───────────┤");
        int i = 1;
        for (ExerciseBean ex : exercises) {
            System.out.printf("  │ %-2d │ %-24s │ %-9s │%n",
                    i++,
                    truncate(ex.getExerciseName(), 24),
                    ex.getSets() + " x " + ex.getReps());
        }
        System.out.println("  └────┴──────────────────────────┴───────────┘");
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }

    private static void printHeader(String title) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.printf( "║  %-28s║%n", "FitConnect — " + title);
        System.out.println("╚══════════════════════════════╝");
    }

    private void removeExercise(Scanner sc) {
        while (true) {
            List<ExerciseBean> exercises=navigator.getExercises();
            printExerciseTable(exercises);
            System.out.println("Seleziona esercizio da rimuovere:(0 per ritornare a crea piano)");
            int choice=readValidIndex(sc,exercises);
            if (choice==0){
                return;
            }
            navigator.deleteExercise(exercises.get(choice-1));
        }
    }
    private int readValidIndex(Scanner sc,List<ExerciseBean> exercises) {
            while (true) {
                String v = sc.nextLine().trim();
                try {
                    int n = Integer.parseInt(v);
                    //exercise indexing starts from 1
                    if (n >= 0 && n<exercises.size()+1) return n;
                    System.out.print(" Inserisci un numero >= 0 e < di "+exercises.size());
                } catch (NumberFormatException e) {
                    System.out.print(" Valore non valido. ");
                }
            }
    }

}