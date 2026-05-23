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
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    if (doLogin(sc, false)) {
                        System.out.println("Login riuscito");
                        return;
                    }
                }
                case "2" -> {
                    if (doLogin(sc, true)) {
                        System.out.println("Login riuscito");
                        return;
                    }
                }
                case "0" -> {
                    System.out.println("\nArrivederci!");
                    System.exit(0);
                }
                default -> System.out.println("[!] Scelta non valida.\n");
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
            if (!isTrainer) {
                AthleteBean bean = new AthleteBean(email, password);
                SessionBean session = ctrl.logAsAthlete(bean);
                navigator.setSession(session);
                navigator.setAthlete(session.getAthlete());
                navigator.goToAthleteDashboard();
            } else {
                PersonalTrainerBean bean = new PersonalTrainerBean(email, password);
                SessionBean session = ctrl.logAsPersonalTrainer(bean);
                navigator.setSession(session);
                navigator.setPt(session.getPt());
                navigator.goToTrainerDashboard();
            }
            return true;
        } catch (ControllerException e) {
            System.out.println("[!] Login non riuscito. Riprova.\n");
            return false;
        } catch (InvalidCredentialsException d) {
            System.out.println("Credenziali non valide.");
            return false;
        }
    }

    // ─────────────────────────────────────────
    private static void printHeader(String title) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.printf("║  %-28s║%n", "FitConnect — " + title);
        System.out.println("╚══════════════════════════════╝");
    }
}

