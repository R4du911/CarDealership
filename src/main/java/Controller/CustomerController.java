package Controller;

import Model.*;
import View.CustomerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerController extends Controller{
    final private Customer model;
    final private CustomerView view;

    public CustomerController(Customer model, CustomerView view) {
        this.model = model;
        this.view = view;
    }

    public List<Order> getOrders() {
        return model.getOrders();
    }

    public Double getMoney() {
        return model.getMoney();
    }

    public List<Car> getAllCars() {
        return model.getAllCars();
    }

    public List<Part> getAllParts() {
        return model.getAllParts();
    }

    public void addOrder(Date date) {
        try {
            model.addOrder(date);
        }catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    public void addProductToList(int ID) {
        try {
            model.addProductToList(ID);
        }catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    public void addProductToShoppingListDatabase(int IDProduct){
        model.addProductToShoppingListDatabase(IDProduct);
    }

    public void removeProductFromList(int ID) {
        try {
            model.removeProductFromList(ID);
        }catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    public void removeProductFromShoppingListDatabase(int IDProd){
        model.removeProductFromShoppingListDatabase(IDProd);
    }

    public List<Merchandise> viewPendingOrder() {
        return model.viewPendingOrder();
    }

    public void populateShoppingList(){
        model.populateShoppingList();
    }

    public List<Part> getAllPartsForACar(int ID) {
        try {
            return model.getAllPartsForACar(ID);
        }catch (Exception error) {
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Car> getAllCarsForAPart(int ID) {
        try {
            return model.getAllCarsForAPart(ID);
        }catch (Exception error) {
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }
    }

    public Car findMin(){
        Car minCar = null;
        double minPrice = Double.MAX_VALUE;

        for(Car car : this.getAllCars()) {
            if(car.getPrice() < minPrice) {
                minPrice = car.getPrice();
                minCar = car;
            }
        }

        return minCar;
    }

    public void updateViewAllOrders() {
        this.view.printAllOrders(this.getOrders());
    }

    public void updateViewAllCars() {
        this.view.printAllCars(this.getAllCars());
    }

    public void updateViewAllParts() {
        this.view.printAllParts(this.getAllParts());
    }

    public void updateViewPendingOrder() {
        this.view.printPendingOrder(this.viewPendingOrder());
    }

    public void updateViewMoney() {
        this.view.printMoney(this.getMoney());
    }

    public void updateViewPartsForACar(int ID) {
        this.view.printAllParts(this.getAllPartsForACar(ID));
    }

    public void updateViewCarsForAPart(int ID) {
        this.view.printAllCars(this.getAllCarsForAPart(ID));
    }

    public void updateViewMinCar(){
        this.view.printMinCar(this.findMin());
    }
}
