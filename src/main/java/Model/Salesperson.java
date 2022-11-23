package Model;

import java.util.ArrayList;
import java.util.List;

import Errors.CustomIllegalArgument;
import Model.Repo.*;
import Interface.*;

public class Salesperson extends Person implements DealershipSystem {
    private final double salary;
    final private InMemoInventory inMemoInventory;

    public Salesperson(String user, String passwd, String firstName, String lastName, double salary, InMemoInventory inventory) {
        super(user, passwd, firstName, lastName);
        this.salary = salary;
        this.inMemoInventory = inventory;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public void add(Merchandise merch) throws CustomIllegalArgument{
        for(Merchandise product : this.inMemoInventory.getCarsAndParts()){
            if(product.getID() == merch.getID()){
                throw new CustomIllegalArgument("Product with same ID already in warehouse");
            }
        }
        this.inMemoInventory.add_Merch(merch);
    }

    @Override
    public void remove(int ID) throws CustomIllegalArgument{
        boolean found = false;
        for(Merchandise product : this.inMemoInventory.getCarsAndParts()) {
            if (product.getID() == ID) {
                found = true;
                this.inMemoInventory.remove_Merch(ID);
                break;
            }
        }
        if(!found){
            throw new CustomIllegalArgument("Product does not exist");
        }
    }

    @Override
    public void update(Merchandise merch) throws CustomIllegalArgument{
        boolean found = false;
        for (Merchandise product : this.inMemoInventory.getCarsAndParts()) {
            if (product.getID() == merch.getID()) {
                found = true;
                this.inMemoInventory.remove_Merch(product.getID());
                this.inMemoInventory.add_Merch(merch);
                break;
            }
        }
        if(!found){
            throw new CustomIllegalArgument("Product does not exist");
        }
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();

        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch instanceof Car) {
                cars.add((Car) merch);
            }
        }
        return cars;
    }

    @Override
    public List<Part> getAllParts() {
        List<Part> parts = new ArrayList<>();
        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch instanceof Part) {
                parts.add((Part) merch);
            }
        }
        return parts;
    }

    public List<Part> getAllPartsForACar(int ID) throws CustomIllegalArgument {
        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch.getID() == ID && merch instanceof Car) {
                Car car = (Car) merch;
                return car.getUsableParts();
            }
        }
        throw new CustomIllegalArgument("Car does not exist");
    }

    public List<Car> getAllCarsForAPart(int ID) throws CustomIllegalArgument {
        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch.getID() == ID && merch instanceof Part) {
                Part part = (Part) merch;
                return part.getForCars();
            }
        }
        throw new CustomIllegalArgument("Part does not exist");
    }
}


