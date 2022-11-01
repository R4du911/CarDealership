package test;

import Controller.SalespersonController;
import Model.Car;
import Model.Part;
import Model.Repo.InMemoInventory;
import Model.Salesperson;
import View.SalespersonView;

import java.util.ArrayList;
import java.util.List;

class SalespersonTest {
    InMemoInventory inventory = new InMemoInventory();
    Salesperson salesperson = new Salesperson("Admin","admin","Costel","Marculescu",234.0,inventory);
    SalespersonView view;
    SalespersonController salespersonCtrl = new SalespersonController(salesperson,view);
    List<Part> parts = new ArrayList<>();
    List<Car> cars = new ArrayList<>();

    @org.junit.jupiter.api.Test
    void add() {
        Car car = new Car(1, "Ford","Fiesta",1225.0,2005,"Diesel",parts);
        salespersonCtrl.add(car);
        assert(salespersonCtrl.getAllCars().contains(car));

        Part part = new Part(2,"Toyota","XCH-I",34.5,cars);
        salespersonCtrl.add(part);
        assert(salespersonCtrl.getAllParts().contains(part));

        System.out.println("Add working good..");
    }

    @org.junit.jupiter.api.Test
    void remove() {
        Car car = new Car(3, "Ford","Fiesta",1225.0,2005,"Diesel",parts);
        salespersonCtrl.add(car);
        salespersonCtrl.remove(car);
        assert(!salespersonCtrl.getAllCars().contains(car));

        Part part = new Part(4,"Toyota","XCH-I",34.5,cars);
        salespersonCtrl.add(part);
        salespersonCtrl.remove(part);
        assert(!salespersonCtrl.getAllParts().contains(part));

        System.out.println("Remove working good..");
    }

    @org.junit.jupiter.api.Test
    void update() {
        Car car1 = new Car(5, "Ford","Fiesta",1225.0,2005,"Diesel",parts);
        salespersonCtrl.add(car1);
        Car car2 = new Car(5, "Ford","Fiesta",1230.0,2005,"Diesel",parts);
        salespersonCtrl.update(car2);
        assert(salespersonCtrl.getAllCars().get(0).getPrice() == 1230.0);

        Part part1 = new Part(6,"Toyota","XCH-I",34.5,cars);
        salespersonCtrl.add(part1);
        Part part2 = new Part(6,"Toyota","XCH-I",35.5,cars);
        salespersonCtrl.update(part2);
        assert(salespersonCtrl.getAllParts().get(0).getPrice() == 35.5);

        System.out.println("Update working good..");
    }

    @org.junit.jupiter.api.Test
    void getAllCars() {
        Car car1 = new Car(7, "Ford","Fiesta",1225.0,2005,"Diesel",parts);
        Car car2 = new Car(8, "Ford","Focus",1230.0,2010,"Diesel",parts);
        Part part1 = new Part(9,"Toyota","XCH-I",34.5,cars);
        salespersonCtrl.add(car1);
        salespersonCtrl.add(car2);
        salespersonCtrl.add(part1);

        List<Car> returned = new ArrayList<>();
        returned = salespersonCtrl.getAllCars();

        assert(returned.contains(car1));
        assert(returned.contains(car2));
        assert(!returned.contains(part1));

        System.out.println("Display cars works good..");
    }

    @org.junit.jupiter.api.Test
    void getAllParts() {
        Car car1 = new Car(10, "Ford","Fiesta",1225.0,2005,"Diesel",parts);
        Part part1 = new Part(11,"Toyota","XCH-I",34.5,cars);
        Part part2 = new Part(12,"Toyota","X76-I",50.1,cars);
        salespersonCtrl.add(car1);
        salespersonCtrl.add(part1);
        salespersonCtrl.add(part2);

        List<Part> returned = new ArrayList<>();
        returned = salespersonCtrl.getAllParts();

        assert(returned.contains(part1));
        assert(returned.contains(part2));
        assert(!returned.contains(car1));

        System.out.println("Display parts works good..");
    }
}