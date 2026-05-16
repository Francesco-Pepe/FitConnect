package controller;

import api.ExerciseApiService;
import api.ExternalApiExerciseDTO;
import api.RealExerciseApiService;
import bean.*;
import dao.athlete.AthleteDAO;
import dao.personaltrainer.PersonalTrainerDAO;
import dao.planrequest.PlanRequestDAO;
import dao.trainingplan.TrainingPlanDAO;
import eng.DAOFactory;
import eng.ExerciseMapper;
import exception.ControllerException;
import exception.DAOException;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageCustomPlanController {

    private final DAOFactory factory         = DAOFactory.getInstance();
    private final AthleteDAO athleteDAO      = factory.getAthleteDAO();
    private final PersonalTrainerDAO ptDAO   = factory.getPersonalTrainerDAO();
    private final PlanRequestDAO requestDAO  = factory.getPlanRequestDAO();
    private final TrainingPlanDAO planDAO    = factory.getTrainingPlanDAO();
    private final ExerciseApiService api     = new RealExerciseApiService();

    public ManageCustomPlanController() throws IOException {
    }

    // ==========================================
    // LATO ATLETA — invia una richiesta al PT
    // ==========================================

    public void sendPlanRequest(PlanRequestBean bean) {
        Athlete athlete = athleteDAO.fetchByEmail(bean.getAthleteEmail());
        if (athlete == null)
            throw new DAOException("Atleta non trovato: " + bean.getAthleteEmail());

        PersonalTrainer pt = ptDAO.getByEmail(bean.getPtEmail());
        if (pt == null)
            throw new DAOException("Personal Trainer non trovato: " + bean.getPtEmail());

        if (athlete.getPlan() != null)
            throw new IllegalStateException("L'atleta ha già un piano attivo");

        // id=0: verrà assegnato dal DAO nella save()
        PlanRequest request = new PlanRequest(0, athlete, pt, bean.getGoal());
        requestDAO.save(request); // save assegna l'id corretto

        // aggiorna le relazioni bidirezionali in memoria
        athlete.addRequest(request);
        pt.addRequest(request);

        // persiste i cambiamenti su atleta e PT
        athleteDAO.update(athlete);
        ptDAO.update(pt);
    }

    // ==========================================
    // LATO ATLETA — vede le proprie richieste
    // ==========================================

    public List<PlanRequest> getAthleteRequests(String athleteEmail) {
        Athlete athlete = athleteDAO.fetchByEmail(athleteEmail);
        if (athlete == null)
            throw new DAOException("Atleta non trovato: " + athleteEmail);
        return requestDAO.fetchByAthlete(athleteEmail);
    }

    // ==========================================
    // LATO ATLETA — vede il proprio piano
    // ==========================================

    public TrainingPlanBean getAthletePlan(String athleteEmail) {
        TrainingPlan plan = planDAO.fetchByAthlete(athleteEmail);
        if (plan == null)
            throw new DAOException("Piano per atleta non trovato: " + athleteEmail);
        List <Exercise> exercises=plan.getExercises();
        List <ExerciseBean> ex=showExercises(exercises);
        return new TrainingPlanBean(plan.getCreationDate(),plan.getExpirationDate(),ex);

    }

    private List<ExerciseBean> showExercises(List<Exercise> exercises) {
        List<ExerciseBean> exs = new ArrayList<>();
        ExerciseBean ex;
        for (Exercise e : exercises) {
            ex = new ExerciseBean(e.getName(), e.getSets(), e.getReps(), e.getExecutionDetails());
            exs.add(ex);

        }
        return exs;
    }

    private List<Technique> getTecniques(Exercise ex) {
        List<Technique> technique = new ArrayList<>();
        Exercise current = ex;
        while (current instanceof ExerciseDecorator ed) {
            if (current instanceof DropSetDecorator)
                technique.add(Technique.DROP_SET);
            else if (current instanceof RestPauseDecorator)
                technique.add(Technique.REST_PAUSE);
            else if (current instanceof SlowEccentricDecorator)
                technique.add(Technique.SLOW_ECCENTRIC);
            else if (current instanceof IsometricPauseDecorator)
                technique.add(Technique.ISOMETRIC_PAUSE);
            else if (current instanceof ForcedRepsDecorator)
                technique.add(Technique.FORCED_REPS);
        }
        return technique;
    }

// ==========================================
// LATO PT — vede le richieste pending
// ==========================================

public List<PlanRequestBean> getPendingRequests(String ptEmail) {
    PersonalTrainer pt = ptDAO.getByEmail(ptEmail);
    if (pt == null)
        throw new DAOException("Personal Trainer non trovato: " + ptEmail);
    List<PlanRequest> requests= requestDAO.fetchPendingByTrainer(ptEmail);
    List<PlanRequestBean> beans=new ArrayList<>();
    for (PlanRequest req:requests){
        Athlete a=req.getClient();
        String athlete=a.getName()+" "+a.getSurname();
        PlanRequestBean bean=new PlanRequestBean(req.getClient().getEmail(),req.getPt().getEmail(),req.getGoal());
        bean.setAthlete(athlete);
        bean.setId(req.getId());
        beans.add(bean);
    }
    return beans;
}

public void declineRequest(PlanRequestBean req){
        try {
            PlanRequest request = requestDAO.getById(req.getId());
            request.setStatus(RequestStatus.REJECTED);
            requestDAO.update(request);

        }catch (DAOException d){
            throw new ControllerException("errore nel recupero della richiesta",d);
        }

}
// ==========================================
// LATO PT — accetta la richiesta e crea il piano
// ==========================================

public void acceptRequestAndCreatePlan(PlanRequestBean bean) {/*
    PlanRequest request = requestDAO.getById(bean.getId());
    if (request == null)
        throw new DAOException("Richiesta non trovata, id: " + bean.getId());

    if (request.getStatus() != RequestStatus.PENDING)
        throw new IllegalStateException("La richiesta non è più in stato PENDING");

    Athlete athlete    = request.getClient();
    PersonalTrainer pt = request.getPt();

    // aggiorna lo stato della richiesta
    request.setStatus(RequestStatus.ACCEPTED);
    requestDAO.update(request);

    // crea il piano
    TrainingPlan plan = new TrainingPlan(athlete, pt, bean.getExpiration());

    // costruisce e aggiunge gli esercizi
    for (Exercise ex : buildExercises(bean.getExercises())) {
        plan.addExercise(ex);
    }

    // aggiorna le relazioni bidirezionali
    athlete.setPlan(plan);
    athlete.setPt(pt);
    pt.addAthlete(athlete);

    // persiste tutto
    planDAO.save(plan);
    athleteDAO.update(athlete);
    ptDAO.update(pt); */
}

// ==========================================
// LATO PT — rifiuta la richiesta
// ==========================================

public void rejectRequest(int requestId) {
    PlanRequest request = requestDAO.getById(requestId);
    if (request == null)
        throw new DAOException("Richiesta non trovata, id: " + requestId);

    if (request.getStatus() != RequestStatus.PENDING)
        throw new IllegalStateException("La richiesta non è più in stato PENDING");

    request.setStatus(RequestStatus.REJECTED);
    requestDAO.update(request);
}

// ==========================================
// PRIVATO — costruisce gli esercizi dal bean
// ==========================================

private List<Exercise> buildExercises(List<ExerciseBean> exerciseBeans) {
    List<Exercise> result = new ArrayList<>();
    for (ExerciseBean bean : exerciseBeans) {
        try {
            ExternalApiExerciseDTO dto = api.fetchExerciseByName(bean.getExerciseName());
            Exercise exercise = ExerciseMapper.fromDTO(dto, bean.getSets(), bean.getReps());
            exercise = applyDecorators(exercise, bean.getTechniques());
            result.add(exercise);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Errore API per esercizio: " + bean.getExerciseName(), e);
        }
    }
    return result;
}

private Exercise applyDecorators(Exercise exercise, List<Technique> techniques) {
    if (techniques == null || techniques.isEmpty()) return exercise;
    for (Technique technique : techniques) {
        exercise = switch (technique) {
            case Technique.DROP_SET        -> new DropSetDecorator(exercise);
            case Technique.REST_PAUSE      -> new RestPauseDecorator(exercise);
            case Technique.SLOW_ECCENTRIC  -> new SlowEccentricDecorator(exercise);
            case Technique.FORCED_REPS     -> new ForcedRepsDecorator(exercise);
            case Technique.ISOMETRIC_PAUSE -> new IsometricPauseDecorator(exercise);
            default -> exercise;
        };
    }
    return exercise;
}

public List<PersonalTrainerBean> retrievePT(){
    List<PersonalTrainer> pts=ptDAO.fetchAll();
    List<PersonalTrainerBean> ptBeans=new ArrayList<>();
    for (PersonalTrainer p:pts){
        PersonalTrainerBean pt=new PersonalTrainerBean(p.getEmail(),"");
        pt.setName(p.getName());
        pt.setSurname(p.getSurname());
        ptBeans.add(pt);
    }
    return ptBeans;
}

public void logout(int id){
        SessionManager.getInstance().deleteSession(id);
}





}

