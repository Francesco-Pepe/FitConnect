package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingPlan {

    private Athlete client;
    private PersonalTrainer creator;

    private LocalDate creationDate;
    private LocalDate expirationDate; // Utile per sapere quando va rifatta la scheda
    private String generalNotes;      // Es. "Fai sempre 10 min di riscaldamento"
    private List<Exercise> exercises;

    public TrainingPlan(Athlete client, PersonalTrainer creator, LocalDate expirationDate, String generalNotes) {
        this.client = client;
        this.creator = creator;
        this.creationDate = LocalDate.now();
        this.expirationDate = expirationDate;
        this.generalNotes = generalNotes;
        this.exercises = new ArrayList<>();
    }

    // 5. Metodo di business specifico per aggiungere un esercizio
    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    public Athlete getClient() { return client; }
    public void setClient(Athlete client) { this.client = client; }

    public PersonalTrainer getCreator() { return creator; }
    public void setCreator(PersonalTrainer creator) { this.creator = creator; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public String getGeneralNotes() { return generalNotes; }
    public void setGeneralNotes(String generalNotes) { this.generalNotes = generalNotes; }

    public List<Exercise> getExercises() { return exercises; }
    public void setExercises(List<Exercise> exercises) { this.exercises = exercises; }
}