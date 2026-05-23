package view;

import bean.AthleteBean;
import bean.PersonalTrainerBean;
import bean.SessionBean;
import controller.LoginController;
import exception.ControllerException;
import exception.InvalidCredentialsException;
import java.util.Scanner;

public class LoginGraphicControllerCLI {

    private Navigator navigator;

    public void setNavigator(Navigator n) {
        this.navigator = n;
    }

    public void start(Scanner sc) {
        while (true) {
            printHeader("LOGIN");
            System.out.println("  [1] Accedi come Atleta");
            System.out.println("  [2] Accedi come Personal Trainer");
            System.out.println("  [0] Esci");
            System.out.print("\n> Scelta: ");
            switch (sc.nextLine().trim()) {
                case "1" -> { if (!doLogin(sc, false)) break; return; }
                case "2" -> { if (!doLogin(sc, true))  break; return; }
                case "0" -> { System.out.println("\nArrivederci!"); System.exit(0); }
                default  -> System.out.println("[!] Scelta non valida.\n");
            }
        }
    }

    private boolean doLogin(Scanner sc, boolean isTrainer) {
        System.out.print("  Email    : ");
        String email = sc.nextLine().trim();
        System.out.print("  Password : ");
        String password = sc.nextLine().trim();
        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("[!] Email e password non possono essere vuoti.\n");
            return false;
        }
        try {
            LoginController ctrl = new LoginController();
            if (isTrainer) loginAsTrainer(ctrl, email, password);
            else           loginAsAthlete(ctrl, email, password);
        } catch (ControllerException e) {
            System.out.println("[!] Login non riuscito. Riprova.\n");
            return false;
        } catch (InvalidCredentialsException e) {
            System.out.println("Credenziali non valide.\n");
            return false;
        }
        return true;
    }

    private void loginAsAthlete(LoginController ctrl, String email, String password) {
        SessionBean session = ctrl.logAsAthlete(new AthleteBean(email, password));
        navigator.setSession(session);
        navigator.setAthlete(session.getAthlete());
        navigator.goToAthleteDashboard();
    }

    private void loginAsTrainer(LoginController ctrl, String email, String password) {
        SessionBean session = ctrl.logAsPersonalTrainer(new PersonalTrainerBean(email, password));
        navigator.setSession(session);
        navigator.setPt(session.getPt());
        navigator.goToTrainerDashboard();
    }

    private static void printHeader(String title) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.printf("║  %-28s║%n", "FitConnect — " + title);
        System.out.println("╚══════════════════════════════╝");
    }
}