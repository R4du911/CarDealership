package Controller;

import Model.Car;
import Model.Merchandise;
import Model.Part;
import Model.Salesperson;
import View.SalespersonView;

import java.util.List;

public class SalespersonController {
    final private Salesperson model;
    final private SalespersonView view;


    public SalespersonController(Salesperson model, SalespersonView view) {
        this.model = model;
        this.view = view;
    }

    public double getSalary() {
        return model.getSalary();
    }

    public void setSalary(Double salary) {
        model.setSalary(salary);
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

    public void updateViewAllCars(){
        List<Car> allCars = this.getAllCars();
        this.view.printAllCars(allCars);
    }

    public void updateViewAllParts(){
        List<Part> allParts = this.getAllParts();
        this.view.printAllParts(allParts);
    }

    public void updateViewSalary(){
        double salary = this.getSalary();
        this.view.printSalary(salary);
    }

    public void updateViewPartsForACar(int ID){
        List<Part> parts = this.getAllPartsForACar(ID);
        this.view.printAllParts(parts);
    }

    public void updateViewCarsForAPart(int ID){
        List<Car> cars = this.getAllCarsForAPart(ID);
        this.view.printAllCars(cars);
    }

}
