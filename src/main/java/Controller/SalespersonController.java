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

    public void add(Merchandise merch) {
        try {
            model.add(merch);
        }catch(Exception error){
            System.out.println(error.getMessage());
        }
    }

    public void insertNewCarDatabase(int id, String brand, String model_car, double price, int yearOfReg, String motor, List<Part> parts){
        model.insertNewCar(id,brand,model_car,price,yearOfReg,motor,parts);
    }

    public void insertNewPartDatabase(int id, String brand, String model_part, double price, List<Car> cars) {
        model.insertNewPart(id,brand,model_part,price,cars);
    }

    public void remove(int ID) {
        try{
            model.remove(ID);
        }catch(Exception error) {
            System.out.println(error.getMessage());
        }
    }

    public void deleteProductDatabase(int id){
        model.deleteProduct(id);
    }

    public void update(Merchandise merch){
        try {
            model.update(merch);
        }catch(Exception error){
            System.out.println(error.getMessage());
        }
    }

    public void updatePriceDatabase(int id, Double newPrice){
        model.updatePrice(id,newPrice);
    }

    public void updatePartsForCarDatabase(int id, List<Part> parts){
        model.updatePartsForCar(id,parts);
    }

    public void updateCarsForPartDatabase(int id, List<Car> cars){
        model.updateCarsForPart(id,cars);
    }

    public List<Car> getAllCars(){return model.getAllCars();}

    public List<Part> getAllParts(){return model.getAllParts();}

    public List<Part> getAllPartsForACar(int ID){
        try{
            return model.getAllPartsForACar(ID);
        }catch(Exception error){
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Car> getAllCarsForAPart(int ID){
        try {
            return model.getAllCarsForAPart(ID);
        }catch(Exception error){
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Car> filterAllCarsByPrice(int price){
        List<Car> allCars = new ArrayList<>();
        for(Car car : model.getAllCars()){
            if(car.getPrice() < price){
                allCars.add(car);
            }
        }
        return allCars;
    }

    public void updateViewAllCars(){
        this.view.printAllCars(this.getAllCars());
    }

    public void updateViewAllParts(){
        this.view.printAllParts(this.getAllParts());
    }

    public void updateViewSalary(){
        this.view.printSalary(this.getSalary());
    }

    public void updateViewPartsForACar(int ID){
        this.view.printAllParts(this.getAllPartsForACar(ID));
    }

    public void updateViewCarsForAPart(int ID){
        this.view.printAllCars(this.getAllCarsForAPart(ID));
    }

    public void updateViewFilterAllCarsByPrice(int price){
        this.view.printAllCars(this.filterAllCarsByPrice(price));
    }

}
