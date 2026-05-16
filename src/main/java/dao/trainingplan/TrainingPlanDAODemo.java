package dao.trainingplan;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingPlanDAODemo extends TrainingPlanDAO {

    // Dati fissi in RAM — coerenti con AthleteDAODemo e PersonalTrainerDAODemo
    private final List<TrainingPlan> plans = new ArrayList<>();

    public TrainingPlanDAODemo() {
        // Atleta e PT di riferimento (stessi dati delle altre Demo)
        Athlete frank = new Athlete("frank@gmail.com", "Francesco", "Pepe", 78, 178, Gender.MALE);
        PersonalTrainer mario = new PersonalTrainer("trainer@fit.com", "Mario", "Rossi", Gender.MALE);

        // Piano di Frank con due esercizi: uno semplice, uno decorato
        TrainingPlan frankPlan = new TrainingPlan(
                frank,
                mario,
                LocalDate.now().plusMonths(3)
        );

        // Esercizio 1: bench press con Drop Set + Rest Pause
        Exercise benchPress = new BaseExercise("bench press", 8, 4, "barbell", "pectorals");
        benchPress = new DropSetDecorator(benchPress);
        benchPress = new RestPauseDecorator(benchPress);
        frankPlan.addExercise(benchPress);

        // Esercizio 2: squat semplice, nessun decoratore
        Exercise squat = new BaseExercise("squat", 5, 5, "barbell", "quads");
        frankPlan.addExercise(squat);

        // Esercizio 3: lat pulldown con Slow Eccentric
        Exercise latPulldown = new BaseExercise("lat pulldown", 10, 3, "cable", "lats");
        latPulldown = new SlowEccentricDecorator(latPulldown);
        frankPlan.addExercise(latPulldown);
        frank.setPlan(frankPlan);
        frank.setPt(mario);
        mario.addAthlete(frank);
        plans.add(frankPlan);

        // Sara non ha ancora un piano (caso realistico: richiesta in attesa)
    }


    // ==========================================
    // METODI ASTRATTI — implementazioni Demo
    // ==========================================

    @Override
    public TrainingPlan searchByAthlete(String athleteEmail) {
        return plans.stream()
                .filter(p -> p.getClient().getEmail().equals(athleteEmail))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TrainingPlan> searchByPersonalTrainer(String ptEmail) {
        return plans.stream()
                .filter(p -> p.getCreator().getEmail().equals(ptEmail))
                .toList();
    }
    @Override
    public void save(TrainingPlan plan) {
        // In RAM il piano è già in memoria dopo addToCache,
        // ma lo aggiungiamo alla lista per coerenza con searchByPersonalTrainer
        boolean exists = plans.stream()
                .anyMatch(p -> p.getClient().getEmail().equals(plan.getClient().getEmail()));
        if (!exists) {
            plans.add(plan);
        }
    }

    @Override
    public void deleteFromStorage(TrainingPlan plan) {
        // In RAM basta rimuovere dalla lista — la cache è già pulita dalla classe astratta
        plans.removeIf(p -> p.getClient().getEmail().equals(plan.getClient().getEmail()));
    }
}