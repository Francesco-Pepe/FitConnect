package model;

public class Exercise {
    private String name;
    private String targetMuscle;
    private String image_url;
    private int sets;
    private int reps;
    private String restTime;
    private String customNotes;

    public Exercise(String name, String restTime, String targetMuscle, String image_url, int sets, int reps, String customNotes) {
        this.name = name;
        this.restTime = restTime;
        this.targetMuscle = targetMuscle;
        this.image_url = image_url;
        this.sets = sets;
        this.reps = reps;
        this.customNotes = customNotes;
    }

    public String getCustomNotes() {
        return customNotes;
    }

    public void setCustomNotes(String customNotes) {
        this.customNotes = customNotes;
    }

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTargetMuscle() {
        return targetMuscle;
    }

    public void setTargetMuscle(String targetMuscle) {
        this.targetMuscle = targetMuscle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
