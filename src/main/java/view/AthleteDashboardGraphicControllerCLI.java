package view;

import bean.AthleteBean;
import bean.SessionBean;
import bean.TrainingPlanBean;
import controller.ManageCustomPlanController;
import exception.UnavailableServiceException;
import java.util.Scanner;

    public class AthleteDashboardGraphicControllerCLI {

        private Navigator navigator;

        public void setNavigator(Navigator n) {
            this.navigator = n;
        }

        public void start(Scanner sc) {
            SessionBean session = navigator.getSession();
            AthleteBean athlete = session.getAthlete();

            printHeader("ATHLETE DASHBOARD");
            System.out.printf("  Benvenuto, %s %s!%n", athlete.getName(), athlete.getSurname());

            // Carica il piano se l'atleta ha un PT assegnato
            boolean hasPlan = false;
            if (athlete.getTrainer() != null) {
                try {
                    ManageCustomPlanController ctrl = new ManageCustomPlanController();
                    TrainingPlanBean plan = ctrl.getAthletePlan(athlete.getEmail());
                    if (plan != null) {
                        navigator.setPlan(plan);
                        navigator.setTrainerName(athlete.getTrainer());
                        hasPlan = true;
                        System.out.println("\n  ┌─ Il tuo piano di allenamento ───────────────┐");
                        System.out.printf( "  │  PT         : %-30s│%n", athlete.getTrainer());
                        System.out.printf( "  │  Creazione  : %-30s│%n", plan.getCreation());
                        System.out.printf( "  │  Scadenza   : %-30s│%n", plan.getExpiration());
                        System.out.printf( "  │  Esercizi   : %-30s│%n", plan.getExercises().size());
                        System.out.println("  └─────────────────────────────────────────────┘");
                    }
                } catch (UnavailableServiceException e) {
                    System.out.println("  [!] Il servizio non è al momento disponibile: " );
                }
            }

            System.out.println();
            if (hasPlan) System.out.println("  [1] Visualizza piano");
            System.out.println("  [2] Richiedi nuovo piano");
            System.out.println("  [0] Logout");
            System.out.print("\n> Scelta: ");

            while (true) {
                String choice = sc.nextLine().trim();
                switch (choice) {
                    case "1" -> {
                        if (hasPlan) { navigator.goToViewPLan(); return; }
                        else System.out.print("[!] Nessun piano disponibile. Scelta: ");
                    }
                    case "2" -> { navigator.goToPlanRequest(); return; }
                    case "0" -> {
                        try {
                            new ManageCustomPlanController().logout(session.getId());
                        } catch (UnavailableServiceException e) {
                            System.out.println("[!] Servizio non disponibile " );
                        }
                        navigator.goToLogin();
                        return;
                    }
                    default -> System.out.print("[!] Scelta non valida: ");
                }
            }
        }

        private static void printHeader(String title) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.printf( "║  %-28s║%n", "FitConnect — " + title);
            System.out.println("╚══════════════════════════════╝");
        }
    }

