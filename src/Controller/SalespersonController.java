package Controller;

import Model.Car;
import Model.Merchandise;
import Model.Part;
import Model.Salesperson;

import java.util.List;

public class SalespersonController {
    final private Salesperson model;
    final private Salesperson view;


    public SalespersonController(Salesperson model, Salesperson view) {
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

    public void remove(Merchandise merch) {
        model.remove(merch);
    }

    public void update(Merchandise merch){model.update(merch);}

    public List<Car> getAllCars(){return model.getAllCars();}

    public List<Part> getAllParts(){return model.getAllParts();}

    }
