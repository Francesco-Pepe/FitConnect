package dao.planrequest;

import eng.CachedDAO;
import model.PlanRequest;
import java.util.List;

public abstract class PlanRequestDAO extends CachedDAO<PlanRequest> {

    @Override
    public String fetchKey(PlanRequest request) {
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

    protected abstract PlanRequest searchRequestById(int id);


    public abstract void save(PlanRequest request);

    public abstract void update(PlanRequest request);
    public abstract List<PlanRequest> fetchByAthlete(String athleteEmail);
    public abstract List<PlanRequest> fetchPendingByTrainer(String trainerEmail);
    public abstract int getMaxId();
}
