package model;

public abstract class ExerciseDecorator implements Exercise {
    protected Exercise wrapperExercise;
    public ExerciseDecorator(Exercise wrapperExercise){
        this.wrapperExercise=wrapperExercise;
    }

    @Override
    public String getName() {
        return this.wrapperExercise.getName();
    }

    @Override
    public String getImageUrl() {
        return this.wrapperExercise.getImageUrl();
    }

    @Override
    public int getReps(){
        return this.wrapperExercise.getReps();
    }

    @Override public int getSets(){
        return this.wrapperExercise.getSets();
    }

    @Override
    public String getExecutionDetails() {
        return this.wrapperExercise.getExecutionDetails();
    }
}
