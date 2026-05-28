package model;

import java.time.LocalDate;
import java.util.List;

public class TrainingPlan {

    private final String client;
    private LocalDate creationDate;
    private LocalDate expirationDate; // Utile per sapere quando va rifatta la scheda
    private List<Exercise> exercises;

    public TrainingPlan(String client,LocalDate creationDate,LocalDate expirationDate,List<Exercise> exercises){
        this.client=client;
        this.creationDate=creationDate;
        this.expirationDate=expirationDate;
        this.exercises=exercises;
    }


    public String getClient() { return client; }

    public LocalDate getCreationDate() { return creationDate; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public List<Exercise> getExercises() { return exercises; }
    public void setExercises(List<Exercise> exercises) { this.exercises = exercises; }
}