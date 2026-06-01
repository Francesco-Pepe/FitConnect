package controller;
import api.ExerciseApiService;
import api.ExternalApiExerciseDTO;
import api.RealExerciseApiService;
import bean.*;
import bean.enums.Event;
import bean.enums.Technique;
import dao.athlete.AthleteDAO;
import dao.personaltrainer.PersonalTrainerDAO;
import dao.planrequest.PlanRequestDAO;
import dao.trainingplan.TrainingPlanDAO;
import eng.DAOFactory;
import eng.ExerciseMapper;
import exception.BusinessException;
import exception.ControllerException;
import exception.DAOException;
import exception.UnavailableServiceException;
import model.*;
import view.boundary.AthleteBoundary;
import view.boundary.TrainerBoundary;
import bean.PlanRequestBean;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ManageCustomPlanController {

    private final DAOFactory factory         = DAOFactory.getInstance();
    private final AthleteDAO athleteDAO      = factory.getAthleteDAO();
    private final PersonalTrainerDAO ptDAO   = factory.getPersonalTrainerDAO();
    private final PlanRequestDAO requestDAO  = factory.getPlanRequestDAO();
    private final TrainingPlanDAO planDAO    = factory.getTrainingPlanDAO();
    private final ExerciseApiService api;

    public ManageCustomPlanController() {
        try {
            this.api=new RealExerciseApiService();
        } catch (IOException e) {
            throw new UnavailableServiceException(e.getMessage());
        }

    }

    //ATHLETE
    public void sendPlanRequest(PlanRequestBean bean) {
        Athlete athlete;
        try {
            athlete = athleteDAO.fetchByEmail(bean.getAthleteEmail());
        }catch (DAOException d){
            throw new ControllerException("Error retrieving the athlete");
        }
        if (athlete == null)
            throw new ControllerException("Athlete not found: " + bean.getAthleteEmail());

        if (athlete.getPlan() != null)
            throw new BusinessException("The athlete already has  an active plan");
        List<PlanRequest> requests=requestDAO.fetchByAthlete(bean.getAthleteEmail());
        if (!requests.isEmpty()) {
            for (PlanRequest req : requests) {
                if (req.getStatus().equals(RequestStatus.PENDING)) {
                    throw new BusinessException("The athlete already has a request pending");
                }
            }
        }
        PlanRequest request = new PlanRequest(requestDAO.getMaxId(), bean.getAthleteEmail(), bean.getPtEmail(), bean.getGoal());
        requestDAO.save(request);
        String athleteName=athlete.getName()+ " " +athlete.getSurname();
        NotificaBean notify=new NotificaBean(athleteName, bean.getPtEmail(), LocalDateTime.now(), Event.NEW_REQUEST);
        TrainerBoundary trainerBoundary=new TrainerBoundary();
        trainerBoundary.sendNotification(notify);

    }


    //To communicate with view
    public TrainingPlanBean getAthletePlan(String athleteEmail) {
        TrainingPlan plan;
        try {
            plan = planDAO.fetchByAthlete(athleteEmail);
        }catch (DAOException e){
            throw new ControllerException("Error retrieving training plan");
        }
        if (plan == null)
            throw new ControllerException("No plan found for: " + athleteEmail);
        List <Exercise> exercises=plan.getExercises();
        List <ExerciseBean> ex=showExercises(exercises);
        return new TrainingPlanBean(plan.getCreationDate(),plan.getExpirationDate(),ex);
    }

    //helper for presenting the plan exercises to the view
    private List<ExerciseBean> showExercises(List<Exercise> exercises) {
        List<ExerciseBean> exs = new ArrayList<>();
        ExerciseBean ex;
        for (Exercise e : exercises) {
            ex = new ExerciseBean(e.getName(), e.getSets(), e.getReps(), e.getExecutionDetails());
            exs.add(ex);
        }
        return exs;
    }

//PERSONAL TRAINER
public List<PlanRequestBean> getPendingRequests(String ptEmail) {
    PersonalTrainer pt;
    List<PlanRequest> requests;
    try {
        pt = ptDAO.fetchPtByEmail(ptEmail);
        requests= requestDAO.fetchPendingByTrainer(ptEmail);
    } catch (DAOException d) {
        throw new ControllerException("Error retrieving the requests");
    }
    if (pt == null)
        throw new ControllerException("Personal Trainer not found: " + ptEmail);

    List<PlanRequestBean> beans=new ArrayList<>();
    for (PlanRequest req:requests){
        Athlete a=athleteDAO.fetchByEmail(req.getClientEmail());
        //non possono esserci errori,la richiesta viene necessariamente da un atleta presente nel sistema
        String athlete=a.getName()+" "+a.getSurname();
        PlanRequestBean bean=new PlanRequestBean(req.getClientEmail(),req.getPtEmail(),req.getGoal());
        bean.setAthlete(athlete);
        bean.setId(req.getId());
        beans.add(bean);
    }
    return beans;
}

public void acceptAndCreatePlan(PlanRequestBean request, TrainingPlanBean plan) {
    try {
        PlanRequest req = requestDAO.getById(request.getId());
        //impossibile avere un id non esistente,i bean vengono creati da model presenti in persistenza
        if (req == null) throw new ControllerException("Richiesta non trovata: " + request.getId());

        Athlete athlete = athleteDAO.fetchByEmail(request.getAthleteEmail());
        //sicuro della validità dei dati
        if (athlete == null) throw new ControllerException("Atleta non trovato");
        //sicuro della validità
        PersonalTrainer pt = ptDAO.fetchPtByEmail(request.getPtEmail());
        if (pt == null) throw new ControllerException("PT non trovato");
        List<Exercise> exercises = buildExercises(plan.getExercises());
        TrainingPlan newPlan = new TrainingPlan(request.getAthleteEmail(),plan.getCreation(), plan.getExpiration(),exercises);
        // 3. Aggiorna relazioni
        athlete.assignPlan(pt,newPlan);
        req.accept();
        // 4. Salva TUTTO (ordine importante!)
        requestDAO.update(req);
        planDAO.save(newPlan);      // ← Salva il piano PRIMA dell'atleta
        athleteDAO.update(athlete);  // ← Ora l'atleta può referenziare il piano
        NotificaBean notify=new NotificaBean(request.getAthlete(), request.getPtEmail(), LocalDateTime.now(),Event.PLAN_CREATED);
        AthleteBoundary athleteBoundary=new AthleteBoundary();
        athleteBoundary.sendNotification(notify);

    } catch (DAOException e) {
        throw new ControllerException("Errore accettazione piano", e);
    }
}






// ==========================================
// LATO PT — rifiuta la richiesta
// ==========================================
public void declineRequest(PlanRequestBean req){
    try {
        PlanRequest request = requestDAO.getById(req.getId());
        request.decline();
        requestDAO.update(request);
        NotificaBean notify=new NotificaBean(req.getPtEmail(),req.getAthleteEmail(),LocalDateTime.now(),Event.REQUEST_REJECTED);
        AthleteBoundary athlete=new AthleteBoundary();
        athlete.sendRejection(notify);
    }catch (DAOException d){
        throw new ControllerException("errore nel recupero della richiesta",d);
    }
}

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
            throw new ControllerException("Errore API per esercizio: " + bean.getExerciseName(), e);
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

        };
    }
    return exercise;
}

public List<PersonalTrainerBean> retrievePT(){
    try {
        List<PersonalTrainer> pts = ptDAO.fetchAll();
        List<PersonalTrainerBean> ptBeans = new ArrayList<>();
        for (PersonalTrainer p : pts) {
            PersonalTrainerBean pt = new PersonalTrainerBean(p.getEmail(), "");
            pt.setName(p.getName());
            pt.setSurname(p.getSurname());
            ptBeans.add(pt);
        }
        return ptBeans;
    }catch (DAOException e){
        throw new ControllerException("Errore nel recupero dei personal trainer");
    }
}

public void logout(int id){
        SessionManager.getInstance().deleteSession(id);
}


}

