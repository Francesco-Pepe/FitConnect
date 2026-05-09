import api.ExerciseApiService;
import api.ExternalApiExerciseDTO;
import api.RealExerciseApiService;

import java.io.IOException;

public class Main {
        public static void main(String[] args) throws IOException, InterruptedException {
            ExerciseApiService api = new RealExerciseApiService();
            System.out.println("Chiamata all'API in corso...");

            ExternalApiExerciseDTO dto = api.fetchExerciseByName("press");

            if (dto != null) {
                System.out.println("SUCCESSO!");
                System.out.println(dto.toString());
            } else {
                System.out.println("FALLITO.");
            }
        }
    }

