package Model;

import java.util.List;

public class Car extends Merchandise {
    private int yearOfReg;
    private String motor;
    private List<Part> usableParts;

    public Car(int ID,String brand, String model, Double price, int yearOfReg, String motor, List<Part> usableParts) {
        super(brand,model,price,ID);
        this.yearOfReg = yearOfReg;
        this.motor = motor;
        this.usableParts = usableParts;
    }

    public int getYearOfReg() {
        return yearOfReg;
    }

    public void setYearOfReg(int yearOfReg) {
        this.yearOfReg = yearOfReg;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public List<Part> getUsableParts() {
        return usableParts;
    }

    public void setUsableParts(List<Part> usableParts) {
        this.usableParts = usableParts;
    }
}


