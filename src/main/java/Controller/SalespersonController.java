package Controller;

import Model.Car;
import Model.Merchandise;
import Model.Part;
import Model.Salesperson;
import View.SalespersonView;

import java.util.ArrayList;
import java.util.List;

public class SalespersonController extends Controller {
    final private Salesperson model;
    final private SalespersonView view;


    public SalespersonController(Salesperson model, SalespersonView view) {
        this.model = model;
        this.view = view;
    }

    public double getSalary() {
        return model.getSalary();
    }

    /**
     * @param merch to be added to the merch-repository
     * Adds a new merch to the locally saved merch-repository
     */
    public void add(Merchandise merch) {
        try {
            model.add(merch);
        }catch(Exception error){
            System.out.println(error.getMessage());
        }
    }

    /**
     * @param id of the car
     * @param brand of the car
     * @param model_car of the car
     * @param price of the car
     * @param yearOfReg of the car
     * @param motor of the car
     * @param parts that can be used on the car
     * Adds a new car to the database saved merch-repository
     */
    public void insertNewCarDatabase(int id, String brand, String model_car, double price, int yearOfReg, String motor, List<Part> parts){
        model.insertNewCar(id,brand,model_car,price,yearOfReg,motor,parts);
    }

    /**
     * @param id of the part
     * @param brand of the part
     * @param model_part of the part
     * @param price of the part
     * @param cars on which the part can be used
     * Adds a new part to the database saved merch-repository
     */
    public void insertNewPartDatabase(int id, String brand, String model_part, double price, List<Car> cars) {
        model.insertNewPart(id,brand,model_part,price,cars);
    }

    /**
     * @param ID of the merch
     * Removes a merch from the locally saved merch-repository
     */
    public void remove(int ID) {
        try{
            model.remove(ID);
        }catch(Exception error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * @param id of the merch
     * Removes a merch from the database saved merch-repository
     */
    public void deleteProductDatabase(int id){
        model.deleteProduct(id);
    }

    /**
     * @param merch to be updated
     * Updates a merch from the locally saved merch-repository
     */
    public void update(Merchandise merch){
        try {
            model.update(merch);
        }catch(Exception error){
            System.out.println(error.getMessage());
        }
    }

    /**
     * @param id of the merch
     * @param newPrice of the merch
     * Updates the price of a merch from the database saved merch-repository
     */
    public void updatePriceDatabase(int id, Double newPrice){
        model.updatePrice(id,newPrice);
    }

    /**
     * @param id of the merch
     * @param parts of the merch
     * Updates the parts that can be used on the given car from the database saved merch-repository
     */
    public void updatePartsForCarDatabase(int id, List<Part> parts){
        model.updatePartsForCar(id,parts);
    }

    /**
     * @param id of the merch
     * @param cars of the merch
     * Updates the cars on which the given part can be used from the database saved merch-repository
     */
    public void updateCarsForPartDatabase(int id, List<Car> cars){
        model.updateCarsForPart(id,cars);
    }

    /**
     * @return all cars from the merch-repository
     */
    public List<Car> getAllCars(){return model.getAllCars();}

    /**
     * @return all parts from the inventory
     */
    public List<Part> getAllParts(){return model.getAllParts();}

    /**
     * @param ID of the car
     * @return all parts that can be used on the given car
     */
    public List<Part> getAllPartsForACar(int ID){
        try{
            return model.getAllPartsForACar(ID);
        }catch(Exception error){
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * @param ID of the part
     * @return all cars on which the part can be used
     */
    public List<Car> getAllCarsForAPart(int ID){
        try {
            return model.getAllCarsForAPart(ID);
        }catch(Exception error){
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * @param price limit
     * @return all cars that are cheaper than the given price
     */
    public List<Car> filterAllCarsByPrice(int price){
        List<Car> allCars = new ArrayList<>();
        for(Car car : model.getAllCars()){
            if(car.getPrice() < price){
                allCars.add(car);
            }
        }
        return allCars;
    }

    /**
     * passes to the view the cars from the merch-repository
     */
    public void updateViewAllCars(){
        this.view.printAllCars(this.getAllCars());
    }

    /**
     * passes to the view the parts from the merch-repository
     */
    public void updateViewAllParts(){
        this.view.printAllParts(this.getAllParts());
    }

    /**
     * passes to the view the salary
     */
    public void updateViewSalary(){
        this.view.printSalary(this.getSalary());
    }

    /**
     * @param ID of the car
     * passes to the view the parts that can be used on the given car
     */
    public void updateViewPartsForACar(int ID){
        this.view.printAllParts(this.getAllPartsForACar(ID));
    }

    /**
     * @param ID of the part
     * passes to the view the cars on which the given part can be used
     */
    public void updateViewCarsForAPart(int ID){
        this.view.printAllCars(this.getAllCarsForAPart(ID));
    }

    /**
     * @param price limit
     * passes to the view the cars that are cheaper than the given price limit
     */
    public void updateViewFilterAllCarsByPrice(int price){
        this.view.printAllCars(this.filterAllCarsByPrice(price));
    }

}
