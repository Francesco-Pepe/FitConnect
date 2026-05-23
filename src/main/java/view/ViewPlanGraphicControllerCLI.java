 package view;

import bean.ExerciseBean;
import bean.TrainingPlanBean;

import java.util.List;
import java.util.Scanner;

public class ViewPlanGraphicControllerCLI {

    private Navigator navigator;

    public void setNavigator(Navigator n) {
        this.navigator = n;
    }

    public void start(Scanner sc) {
        TrainingPlanBean plan = navigator.getPlan();
        String trainerName    = navigator.getTrainerName();

        printHeader("IL TUO PIANO");
        System.out.printf("  PT         : %s%n", trainerName);
        System.out.printf("  Creazione  : %s%n", plan.getCreation());
        System.out.printf("  Scadenza   : %s%n", plan.getExpiration());

        List<ExerciseBean> exercises = plan.getExercises();
        System.out.printf("%n  Esercizi (%d):%n", exercises.size());
        System.out.println("  ┌────┬──────────────────────────┬───────────┬─────────────────────────┐");
        System.out.println("  │ N° │ Esercizio                │ Set x Rep │ Tecniche                │");
        System.out.println("  ├────┼──────────────────────────┼───────────┼─────────────────────────┤");

        int i = 1;
        for (ExerciseBean ex : exercises) {
            String setsReps  = ex.getSets() + " x " + ex.getReps();
            String details   = ex.getExecutionDetails() != null && !ex.getExecutionDetails().isBlank()
                    ? ex.getExecutionDetails() : "—";
            System.out.printf("  │ %-2d │ %-24s │ %-9s │ %-23s │%n",
                    i++,
                    truncate(ex.getExerciseName(), 24),
                    setsReps,
                    truncate(details, 23));
        }
        System.out.println("  └────┴──────────────────────────┴───────────┴─────────────────────────┘");

        System.out.println("\n  [0] Torna alla dashboard");
        System.out.print("\n> Scelta: ");

        while (true) {
            String choice = sc.nextLine().trim();
            if ("0".equals(choice)) {
                navigator.goToAthleteDashboard();
                return;
            } else {
                System.out.print("[!] Scelta non valida: ");
            }
        }
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
}
