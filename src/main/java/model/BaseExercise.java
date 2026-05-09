package model;

public class BaseExercise implements Exercise{
    private String name;
    private String target;
    private String equipment;
    private int sets;
    private int reps;

    public BaseExercise(String name, int reps, int sets, String equipment, String target) {
        this.name = name;
        this.reps = reps;
        this.sets = sets;
        this.equipment = equipment;
        this.target = target;
    }



    @Override
    public String getTarget(){
        return this.target;
    }
    @Override
    public String getEquipment(){
        return this.equipment;
    }
    @Override
    public String getName(){
        return this.name;
    }
    @Override
    public int getSets(){
        return this.sets;
    }
    @Override
    public int getReps(){
        return this.reps;
    }

    @Override
    public String getExecutionDetails(){
        return sets +" sets of " + reps +" reps ";
    }

}
