package eng;
import java.util.HashMap;
import java.util.Map;

public abstract class CachedDAO<T> {

    protected Map<String, T> cache;

    protected CachedDAO() {
        this.cache = new HashMap<>();
    }

    public boolean inCache(String key){
        return this.cache.containsKey(key);
    }

    public boolean inCache (T obj) {
        if(obj == null){
            return false;
        }
        String key = fetchKey(obj);
        return inCache(key);
    }

    protected abstract String fetchKey(T obj);

    public void addToCache(T obj) {
        if (obj != null && (!inCache(obj))) {
            String key = fetchKey(obj);
            this.cache.put(key, obj);
        }
    }

    public T fetchFromCache(String key) {
        return this.cache.get(key);
    }

    public void deleteFromCache(T obj) {
        if(inCache(obj)){
            String key = fetchKey(obj);
            this.cache.remove(key);
        }
    }

    public void svuotaCache() {
        this.cache.clear();
    }
}