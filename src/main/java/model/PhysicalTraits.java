package model;

public class PhysicalTraits {
    private double weight;
    private int height;
    private final Gender gender;

    public PhysicalTraits(double weight, int height, Gender gender) {
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public Gender getGender() {
        return gender;
    }

    public int getHeight() {
        return height;
    }
}
