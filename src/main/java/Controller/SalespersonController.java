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
        model.add(merch);
    }

    public void remove(int ID) {model.remove(ID);}

    public void update(Merchandise merch){model.update(merch);}

    public List<Car> getAllCars(){return model.getAllCars();}

    public List<Part> getAllParts(){return model.getAllParts();}

    public List<Part> getAllPartsForACar(int ID){return model.getAllPartsForACar(ID);}

    public List<Car> getAllCarsForAPart(int ID){return model.getAllCarsForAPart(ID);}

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
