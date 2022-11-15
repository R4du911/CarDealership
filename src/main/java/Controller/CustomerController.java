package Controller;

import Model.*;
import View.CustomerView;

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
        model.addOrder(date);
    }

    public void addProductToList(int ID) {model.addProductToList(ID);}

    public void removeProductFromList(int ID) {
        model.removeProductFromList(ID);
    }

    public List<Merchandise> viewPendingOrder() {
        return model.viewPendingOrder();
    }

    public List<Part> getAllPartsForACar(int ID) {
        return model.getAllPartsForACar(ID);
    }

    public List<Car> getAllCarsForAPart(int ID) {
        return model.getAllCarsForAPart(ID);
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
