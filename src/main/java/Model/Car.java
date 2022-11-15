package Model;

import java.util.List;

public class Car extends Merchandise {
    private final int yearOfReg;
    private final String motor;
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

    public String getMotor() {
        return motor;
    }

    public List<Part> getUsableParts() {
        return usableParts;
    }

    public void setUsableParts(List<Part> usableParts) {
        this.usableParts = usableParts;
    }

    public String toStringCar(){
        return "(ID:" + this.getID() + ", Brand:" + this.getBrand() + ", Model:" + this.getModel() + ", Year Of Reg:" + this.getYearOfReg() + ", Motor:" +
                this.getMotor() + ", Price:" + this.getPrice() + ")";
    }
}


