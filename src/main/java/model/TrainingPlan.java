package model;

import java.time.LocalDate;
import java.util.List;

public class TrainingPlan {


    private LocalDate creationDate;
    private LocalDate expirationDate; // Utile per sapere quando va rifatta la scheda

    public TrainingPlan(Athlete athlete, LocalDate creationDate, LocalDate expirationDate, List<Exercise> exercises) {
        this.athlete = athlete;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.exercises = exercises;
    }

    private List<Exercise> exercises;
    private Athlete athlete;



    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public LocalDate getCreationDate() { return creationDate; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public List<Exercise> getExercises() { return exercises; }
    public void setExercises(List<Exercise> exercises) { this.exercises = exercises; }
}