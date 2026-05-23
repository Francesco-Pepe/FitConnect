package view;

import bean.ExerciseBean;
import bean.Technique;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddExerciseGraphicControllerCLI {

    private Navigator navigator;

    public void setNavigator(Navigator n) {
        this.navigator = n;
    }

    public void start(Scanner sc) {
        printHeader("AGGIUNGI ESERCIZIO");

        // Nome
        String name = readNonEmpty(sc, "  Nome esercizio : ");
        if (name == null) { navigator.goToCreatePlan(); return; }

        // Sets
        int sets = readPositiveInt(sc, "  Numero di set  : ");
        // Reps
        int reps = readPositiveInt(sc, "  Numero di reps : ");

        // Tecniche
        List<Technique> techniques = readTechniques(sc);

        ExerciseBean ex = new ExerciseBean(name, sets, reps, techniques);
        navigator.addExercise(ex);

        System.out.printf("%n  [✓] Esercizio \"%s\" aggiunto (%d set x %d reps).%n", name, sets, reps);
        System.out.print("  Premi INVIO per tornare al piano... ");
        sc.nextLine();

        navigator.goToCreatePlan();
    }

    private List<Technique> readTechniques(Scanner sc) {
        Technique[] all = Technique.values();
        System.out.println("\n  Tecniche speciali (lascia vuoto per saltare):");
        for (int i = 0; i < all.length; i++) {
            System.out.printf("    [%d] %s%n", i + 1, all[i]);
        }
        System.out.print("  Seleziona (es: 1 3 5, oppure INVIO per nessuna): ");

        String line = sc.nextLine().trim();
        List<Technique> selected = new ArrayList<>();
        if (line.isEmpty()) return selected;

        for (String token : line.split("\\s+")) {
            try {
                int idx = Integer.parseInt(token) - 1;
                if (idx >= 0 && idx < all.length && !selected.contains(all[idx])) {
                    selected.add(all[idx]);
                }
            } catch (NumberFormatException ignored) {
                // token non numerico, ignora
            }
        }
        return selected;
    }

    private String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String v = sc.nextLine().trim();
            if (v.equals("0")) return null;
            if (!v.isEmpty()) return v;
            System.out.print("[!] Il campo non può essere vuoto. ");
        }
    }

    private int readPositiveInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String v = sc.nextLine().trim();
            try {
                int n = Integer.parseInt(v);
                if (n > 0) return n;
                System.out.print("[!] Inserisci un numero > 0. ");
            } catch (NumberFormatException e) {
                System.out.print("[!] Valore non valido. ");
            }
        }
    }

    private static void printHeader(String title) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.printf( "║  %-28s║%n", "FitConnect — " + title);
        System.out.println("╚══════════════════════════════╝");
    }
}