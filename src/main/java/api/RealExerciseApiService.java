package api;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.json.*;

public class RealExerciseApiService implements ExerciseApiService {

    private static final String BASE_URL = "https://exercisedb.p.rapidapi.com";
    private final String apiKey;
    private final HttpClient client = HttpClient.newHttpClient();

    public RealExerciseApiService() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        this.apiKey = props.getProperty("api.key");
    }

    private HttpRequest buildRequest(String endpoint) {
        return HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
                .GET()
                .build();
    }

    @Override
    public ExternalApiExerciseDTO fetchExerciseByName(String name) throws IOException, InterruptedException {
        String encoded = URLEncoder.encode(name.toLowerCase().trim(), StandardCharsets.UTF_8)
                .replace("+", "%20");
        HttpRequest req = buildRequest("/exercises/name/" + encoded + "?limit=1");
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        JSONArray arr = new JSONArray(res.body());

        if (arr.isEmpty()) return null;

        JSONObject obj = arr.getJSONObject(0);
        return new ExternalApiExerciseDTO(
                obj.getString("equipment"),
                obj.getString("target"),
                obj.getString("name")
        );
    }
}