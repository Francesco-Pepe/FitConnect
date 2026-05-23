package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingPlan {

    private String client;
    private String creator;
    private LocalDate creationDate;
    private LocalDate expirationDate; // Utile per sapere quando va rifatta la scheda
    private List<Exercise> exercises;

    public TrainingPlan(String client, String creator, LocalDate expirationDate) {
        this.client = client;
        this.creator = creator;
        this.creationDate = LocalDate.now();
        this.expirationDate = expirationDate;
        this.exercises = new ArrayList<>();
    }

    // 5. Metodo di business specifico per aggiungere un esercizio
    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }


    public List<Exercise> getExercises() { return exercises; }
    public void setExercises(List<Exercise> exercises) { this.exercises = exercises; }
}