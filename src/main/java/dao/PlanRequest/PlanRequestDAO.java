package dao.PlanRequest;

import eng.CachedDAO;
import model.PlanRequest;
import java.util.List;

public abstract class PlanRequestDAO extends CachedDAO<PlanRequest> {

    @Override
    public String fetchKey(PlanRequest request) {
        // Trasformiamo l'int in Stringa solo per usarlo come chiave nella HashMap della Cache
        return String.valueOf(request.getId());
    }

    public PlanRequest getById(int id) {
        PlanRequest r;
        String stringId = String.valueOf(id);

        if (inCache(stringId)) {
            r = fetchFromCache(stringId);
        } else {
            r = searchRequestById(id);
            if (r != null) {
                addToCache(r);
            }
        }
        return r;
    }

    // ==========================================
    // METODI ASTRATTI (Il Contratto)
    // ==========================================

    // Metodo interno per pescare la richiesta
    protected abstract PlanRequest searchRequestById(int id);

    // Il metodo salva DEVE assegnare l'ID corretto (currentId++) prima di salvare!
    public abstract void save(PlanRequest request);

    public abstract void update(PlanRequest request);

    // Per la GUI dell'Atleta (vuole vedere lo stato delle sue richieste)
    public abstract List<PlanRequest> fetchByAthlete(String athleteEmail);

    // Per la GUI del PT (vuole vedere le richieste in sospeso)
    public abstract List<PlanRequest> fetchPendingByTrainer(String trainerEmail);
}
