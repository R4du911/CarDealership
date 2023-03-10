package Test;

import Controller.CustomerController;
import Errors.CustomIllegalArgument;
import Model.*;
import Model.Repo.Inventory;
import View.CustomerView;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerTest {
    Inventory inventory = new Inventory();
    Customer customer = new Customer("Admin", "admin", "Costel", "Marculescu", 10000.0, inventory);
    CustomerView view;
    CustomerController customerCtrl = new CustomerController(customer, view);
    List<Part> parts = new ArrayList<>();
    List<Car> cars = new ArrayList<>();

    @Test
    void addProductToList() {
        Car car1 = new Car(1, "Volvo", "xc60", 5500.0, 2010, "Diesel", parts);
        inventory.add_Merch(car1);
        customerCtrl.addProductToList(1);
        assert (customerCtrl.viewPendingOrder().contains(car1));

        Part part1 = new Part(3, "Toyota", "XC-235", 234.0, cars);
        inventory.add_Merch(part1);
        customerCtrl.addProductToList(3);
        assert (customerCtrl.viewPendingOrder().contains(part1));

        CustomIllegalArgument exception = assertThrows(CustomIllegalArgument.class, () -> customer.addProductToList(20));
        assertEquals("Product does not exist", exception.getMessage());

        System.out.println("Add product works good..");
    }

    @Test
    void removeProductFromList() {
        Car car = new Car(3, "Ford", "Fiesta", 1225.0, 2005, "Diesel", parts);
        inventory.add_Merch(car);
        customerCtrl.addProductToList(3);
        customerCtrl.removeProductFromList(3);
        assert (!customerCtrl.viewPendingOrder().contains(car));

        Part part = new Part(4, "Toyota", "XCH-I", 34.5, cars);
        inventory.add_Merch(part);
        customerCtrl.addProductToList(4);
        customerCtrl.removeProductFromList(4);
        assert (!customerCtrl.viewPendingOrder().contains(part));

        CustomIllegalArgument exception = assertThrows(CustomIllegalArgument.class, () -> customer.removeProductFromList(20));
        assertEquals("Product does not exist in pendingOrder", exception.getMessage());

        System.out.println("Remove product works good..");
    }

    @Test
    void viewPendingOrder() {
        Car car1 = new Car(10, "Ford", "Fiesta", 1225.0, 2005, "Diesel", parts);
        Part part1 = new Part(11, "Toyota", "XCH-I", 34.5, cars);
        Part part2 = new Part(12, "Toyota", "X76-I", 50.1, cars);
        inventory.add_Merch(car1);
        inventory.add_Merch(part1);
        inventory.add_Merch(part2);

        customerCtrl.addProductToList(10);
        customerCtrl.addProductToList(11);
        customerCtrl.addProductToList(12);

        List<Merchandise> returned;
        returned = customerCtrl.viewPendingOrder();

        assert (returned.contains(part1));
        assert (returned.contains(part2));
        assert (returned.contains(car1));

        System.out.println("Display chosen products works good..");
    }

    @Test
    void addOrder() {
        Car car1 = new Car(1, "Volvo", "xc60", 5500.0, 2010, "Diesel", parts);
        Car car2 = new Car(2, "Mercedes", "c-class", 3550.0, 2015, "Diesel", parts);
        Part part1 = new Part(3, "Toyota", "XC-235", 234.0, cars);
        inventory.add_Merch(car1);
        inventory.add_Merch(part1);
        inventory.add_Merch(car2);

        customerCtrl.addProductToList(1);
        customerCtrl.addProductToList(3);
        Date date = new Date(2022, Calendar.NOVEMBER, 1);

        customerCtrl.addOrder(date);
        assert (customerCtrl.getOrders().get(0).getProductList().getPurchased().contains(car1));
        assert (customerCtrl.getOrders().get(0).getProductList().getPurchased().contains(part1));
        assert (!customerCtrl.getOrders().get(0).getProductList().getPurchased().contains(car2));

        System.out.println("Add order works good...");

        CustomIllegalArgument exception = assertThrows(CustomIllegalArgument.class, () -> customer.addOrder(date));
        assertEquals("ProductList is empty", exception.getMessage());

        Car car3 = new Car(4, "Volvo", "xc60", 15500.0, 2014, "Gasoline", parts);
        inventory.add_Merch(car1);
        inventory.add_Merch(car3);
        customerCtrl.addProductToList(4);
        CustomIllegalArgument exception2 = assertThrows(CustomIllegalArgument.class, () -> customer.addOrder(date));
        assertEquals("Not enough money", exception2.getMessage());

        System.out.println("Add order exceptions work good...");

    }

    @Test
    void getAllPartsForACar() {
        List<Part> partsForCar = new ArrayList<>();
        Part part1 = new Part(11, "Toyota", "XCH-I", 34.5, cars);
        Part part2 = new Part(12, "Toyota", "X76-I", 50.1, cars);
        partsForCar.add(part1);
        partsForCar.add(part2);

        Car car1 = new Car(1, "Volvo", "xc60", 5500.0, 2010, "Diesel", partsForCar);
        inventory.add_Merch(car1);

        List<Part> returnedPartsForACar;
        returnedPartsForACar = customerCtrl.getAllPartsForACar(1);

        assert (returnedPartsForACar.contains(part1));
        assert (returnedPartsForACar.contains(part2));

        CustomIllegalArgument exception1 = assertThrows(CustomIllegalArgument.class, () -> customer.getAllPartsForACar(11));
        assertEquals("Car does not exist", exception1.getMessage());

        System.out.println("Display parts for a given car works good...");
    }

    @Test
    void getAllCarsForAPart() {
        List<Car> carsForPart = new ArrayList<>();
        Car car1 = new Car(1, "Volvo", "xc60", 5500.0, 2010, "Diesel", parts);
        Car car2 = new Car(2, "Mercedes", "c-class", 3550.0, 2015, "Diesel", parts);

        carsForPart.add(car1);
        carsForPart.add(car2);

        Part part1 = new Part(11, "Toyota", "XCH-I", 34.5, carsForPart);
        inventory.add_Merch(part1);

        List<Car> returnedCarsForAPart;
        returnedCarsForAPart = customerCtrl.getAllCarsForAPart(11);

        assert (returnedCarsForAPart.contains(car1));
        assert (returnedCarsForAPart.contains(car2));

        CustomIllegalArgument exception1 = assertThrows(CustomIllegalArgument.class, () -> customer.getAllCarsForAPart(69));
        assertEquals("Part does not exist", exception1.getMessage());

        System.out.println("Display cars for a given part works good...");
    }
}