package api;

public class ExternalApiExerciseDTO {
    private String name;
    private String targetMuscle;
    private String equipment;

    public ExternalApiExerciseDTO(String equipment, String targetMuscle, String name) {
        this.equipment = equipment;
        this.targetMuscle = targetMuscle;
        this.name = name;
    }

    @Override
    public String toString() {
        return "API DTO: " + name + " target: " +targetMuscle + " equipment: " +equipment;
    }
}
