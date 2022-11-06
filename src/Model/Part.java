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
        String result = "(" + this.getID() + ", " + this.getBrand() + ", " + this.getModel() + ", " + this.getPrice() + ", ";
        for(Car car : this.getForCars()){
            result += car.toStringCar();
        }
        result += ")";
        return result;
    }
}


