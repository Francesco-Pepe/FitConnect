package model;

public abstract class ExerciseDecorator implements Exercise {
    protected Exercise wrapperExercise;
    protected ExerciseDecorator(Exercise wrapperExercise){
        this.wrapperExercise=wrapperExercise;
    }

    public Exercise getWrapperExercise() {
        return this.wrapperExercise;
    }

    @Override
    public String getTarget(){
        return this.wrapperExercise.getTarget();
    }

    @Override
    public String getEquipment(){
        return this.wrapperExercise.getEquipment();
    }

    @Override
    public String getName() {
        return this.wrapperExercise.getName();
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
