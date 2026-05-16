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

    public String getName() {
        return name;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getTarget() {
        return targetMuscle;
    }
}
