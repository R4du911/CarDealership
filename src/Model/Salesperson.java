package Model;

import java.util.ArrayList;
import java.util.List;
import Repo.*;
import Interface.*;

public class Salesperson extends Person implements DealershipSystem {
    private double salary;
    final private InMemoInventory inMemoInventory;

    public Salesperson(String user, String passwd, String firstName, String lastName, double salary, InMemoInventory inventory) {
        super(user, passwd, firstName, lastName);
        this.salary = salary;
        this.inMemoInventory = inventory;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public void add(Merchandise merch) {
        this.inMemoInventory.add_Merch(merch);
    }

    @Override
    public void remove(Merchandise merch) {
        this.inMemoInventory.remove_Merch(merch);
    }

    @Override
    public void update(Merchandise merch) {
        for(Merchandise product: this.inMemoInventory.getCarsAndParts()){
            if(product.getID()==merch.getID()){
                this.inMemoInventory.remove_Merch(product);
                this.inMemoInventory.add_Merch(merch);
                break;
            }
        }
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<Car>();

        for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
            if(merch instanceof Car){
                cars.add((Car) merch);
            }
        }

        return cars;
    }

    @Override
    public List<Part> getAllParts() {
        List<Part> parts  = new ArrayList<Part>();
        for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
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


