package Model;

import java.util.List;

public class Part extends Merchandise {
    private List<Car> forCars;

    public List<Car> getForCars() {
        return forCars;
    }

    public void setForCars(List<Car> forCars) {
        this.forCars = forCars;
    }

    public Part(int ID,String brand, String model, Double price, List<Car> forCars) {
        super(brand, model, price,ID);
        this.forCars = forCars;
    }

    public String toStringPart(){
        String result = "(ID:" + this.getID() + ", Brand:" + this.getBrand() + ", Model:" + this.getModel() + ", Price:" + this.getPrice() + ")" + "\n" +
                "Part can be used for these cars:" + "\n" + "[";
        int count = 1;
        for(Car car : this.getForCars()){
            result += "Car " + count + ": (ID:" + car.getID() + ", Brand:" + car.getBrand() + ", Model:" + car.getModel() + ", Year Of Reg:" + car.getYearOfReg() + ", Motor:" +
                    car.getMotor() + ", Price:" + car.getPrice() + ")" + "\n";
            count += 1;
        }
        result += "])";
        return result;
    }
}


