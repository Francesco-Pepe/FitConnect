package view;

import bean.PersonalTrainerBean;
import bean.PlanRequestBean;
import controller.ManageCustomPlanRequestController;
import exception.ControllerException;
import exception.UnavailableServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrainerDashboardGraphicControllerCLI {

    private Navigator navigator;

    public void setNavigator(Navigator n) {
        this.navigator = n;
    }

    public void start(Scanner sc) {
        PersonalTrainerBean pt = navigator.getPt();
        navigator.setExercises(new ArrayList<>());
        printWelcome(pt);
        ManageCustomPlanRequestController ctrl = buildController();
        if (ctrl == null) return;
        List<PlanRequestBean> requests = loadRequests(ctrl, pt.getEmail());
        printRequests(requests);
        handleLoop(sc, ctrl, new ArrayList<>(requests));
    }

    private ManageCustomPlanRequestController buildController() {
        try {
            return new ManageCustomPlanRequestController();
        } catch (UnavailableServiceException e) {
            System.out.println("Impossibile contattare il database degli esercizi, riprovare più tardi");
            return null;
        }
    }

    private List<PlanRequestBean> loadRequests(ManageCustomPlanRequestController ctrl, String email) {
        try {
            return ctrl.getPendingRequests(email);
        } catch (ControllerException e) {
            System.out.println(" Errore caricamento richieste: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void printRequests(List<PlanRequestBean> requests) {
        if (requests.isEmpty()) System.out.println("\n  Nessuna richiesta pendente.");
        else showRequests(requests);
        showCommands(requests);
    }

    private void handleLoop(Scanner sc, ManageCustomPlanRequestController ctrl, List<PlanRequestBean> requests) {
        while (true) {
            String input = sc.nextLine().trim().toUpperCase();
            if ("0".equals(input)) {
                ctrl.logout(navigator.getSession().getId());
                navigator.goToLogin();
                return;
            }
            if (input.length() >= 2 && (input.startsWith("A") || input.startsWith("D"))) {
                handleAction(input, ctrl, requests);
            } else {
                System.out.print(" Scelta non valida: ");
            }
        }
    }

    private void handleAction(String input, ManageCustomPlanRequestController ctrl, List<PlanRequestBean> requests) {
        char action = input.charAt(0);
        try {
            int idx = Integer.parseInt(input.substring(1)) - 1;
            if (idx < 0 || idx >= requests.size()) {
                System.out.print(" Numero fuori range. Riprova: ");
                return;
            }
            PlanRequestBean req = requests.get(idx);
            if (action == 'A') {
                navigator.setPlanRequest(req);
                navigator.goToCreatePlan();
            } else {
                ctrl.declineRequest(req);
                requests.remove(idx);
                System.out.printf("  Richiesta di %s rifiutata.%n",
                        req.getAthlete() != null ? req.getAthlete() : req.getAthleteEmail());
                if (requests.isEmpty()) System.out.println("  Nessuna altra richiesta pendente.");
                else showRequests(requests);
                System.out.print("> Scelta: ");
            }
        } catch (NumberFormatException e) {
            System.out.print(" Formato non valido (es: A1, D2). Riprova: ");
        } catch (ControllerException e) {
            System.out.print(e.getMessage() + " Riprova: ");
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

    private void printWelcome(PersonalTrainerBean pt) {
        printHeader("TRAINER DASHBOARD");
        System.out.printf("  Benvenuto, %s %s!%n", pt.getName(), pt.getSurname());
    }

    private void showRequests(List<PlanRequestBean> requests) {
        System.out.printf("%n  Richieste pendenti (%d):%n", requests.size());
        System.out.println("  ┌────┬──────────────────────────────┬───────────────────┐");
        System.out.println("  │ N° │ Atleta                       │ Obiettivo         │");
        System.out.println("  ├────┼──────────────────────────────┼───────────────────┤");
        for (int i = 0; i < requests.size(); i++) {
            PlanRequestBean r = requests.get(i);
            System.out.printf("  │ %-2d │ %-28s │ %-17s │%n",
                    i + 1,
                    truncate(r.getAthlete() != null ? r.getAthlete() : r.getAthleteEmail(), 28),
                    r.getGoal());
        }
        System.out.println("  └────┴──────────────────────────────┴───────────────────┘");
    }

    private void showCommands(List<PlanRequestBean> requests) {
        System.out.println();
        if (!requests.isEmpty()) {
            System.out.println("  [A<n>] Accetta richiesta n  (es: A1)");
            System.out.println("  [D<n>] Rifiuta richiesta n  (es: D2)");
        }
        System.out.println("  [0]    Logout");
        System.out.print("\n> Scelta: ");
    }
}