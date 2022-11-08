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
        return "(ID:" + this.getID() + ", Brand:" + this.getBrand() + ", Model:" + this.getModel() + ", Price:" + this.getPrice() + ")" + "\n";
    }
}


