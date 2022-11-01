package test;

import Controller.SalespersonController;
import Model.Car;
import Model.Merchandise;
import Model.Part;
import Model.Repo.InMemoInventory;
import Model.Salesperson;
import View.SalespersonView;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

class SalespersonTest {
    InMemoInventory inventory = new InMemoInventory();
    Salesperson salesperson = new Salesperson("Admin","admin","Costel","Marculescu",234.0,inventory);
    SalespersonView view;
    SalespersonController salespersonCtrl = new SalespersonController(salesperson,view);

    @org.junit.jupiter.api.Test
    void add() {
        List<Part> parts = new ArrayList<>();
        Car car = new Car(2, "Ford","Fiesta",1225.0,2005,"Diesel",parts);
        salespersonCtrl.add(car);
        assert(salespersonCtrl.getAllCars().contains(car));
        System.out.println("Add working good..");
    }

    @org.junit.jupiter.api.Test
    void remove() {
    }

    @org.junit.jupiter.api.Test
    void update() {
    }

    @org.junit.jupiter.api.Test
    void getAllCars() {
    }

    @org.junit.jupiter.api.Test
    void getAllParts() {
    }
}