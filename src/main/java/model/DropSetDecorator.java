package model;

public class DropSetDecorator extends ExerciseDecorator{
    private int setsNumber;
    private int weightReduction;
    public DropSetDecorator(Exercise wrapperExercise) {
        super(wrapperExercise);
        this.setsNumber=2;
        this.weightReduction=20;
    }
    protected void setSetsNumber(int sets){
        this.setsNumber=sets;
    }
    protected void setWeightReduction(int reduction){
        if (reduction >90){
            this.weightReduction=90;
        }
        else {
            this.weightReduction=reduction;
        }
    }

    protected String applyDropset(String input){
        return input+" [Technique: Drop Set on the last "+setsNumber+" sets (reduce weight by "+weightReduction+"% and perform to failure)]";
    }
    @Override
    public String getExecutionDetails(){
        String oldDetails=super.getExecutionDetails();
        return applyDropset(oldDetails);
    }
}
