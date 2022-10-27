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

    public void add(Merchandise merch){

    }

    public void remove(Merchandise merch) {

    }

    public void update(Merchandise merch) {

    }

    public List<Car> getAllCar() {
        return null;
    }


    public List<Part> getAllParts() {
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        return null;
    }

    @Override
    public boolean checkStatus() {
        return false;
    }
}


