package model;

public class BaseExercise implements Exercise{
    private String name;
    private String imageUrl;
    private int sets;
    private int reps;
    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getImageUrl(){
        return this.imageUrl;
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
        return sets +"of" + reps +"reps";
    }

}
