import java.util.ArrayList;
import java.util.List;

public class Salesperson extends Person implements DealershipSystem{
    private double salary;
    private Inventory inventory;

    public Salesperson(String user, String passwd, String firstName, String lastName, double salary, Inventory inventory) {
        super(user, passwd, firstName, lastName);
        this.salary = salary;
        this.inventory = inventory;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void add(Merchandise merch) {
        this.inventory.add_Merch(merch);
    }

    @Override
    public void remove(Merchandise merch) {
        this.inventory.remove_Merch(merch);
    }

    @Override
    public void update(Merchandise merch) {
        for(Merchandise product: this.inventory.getCarsAndParts()){
            if(product.getID()==merch.getID()){
                this.inventory.remove_Merch(product);
                this.inventory.add_Merch(merch);
                break;
            }
        }
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<Car>();

        for(Merchandise merch : this.inventory.getCarsAndParts()){
            if(merch instanceof Car){
                cars.add((Car) merch);
            }
        }

        return cars;
    }

    @Override
    public List<Part> getAllParts() {
        List<Part> parts  = new ArrayList<Part>();
        for(Merchandise merch : this.inventory.getCarsAndParts()){
            if(merch instanceof Part){
                parts.add((Part) merch);
            }
        }
        return parts;
    }



    @Override
    public boolean checkStatus() {
        return false;
    }
}


