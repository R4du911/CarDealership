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

    public void setMoney(Double money) {
        model.setMoney(money);
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

    public void addProductToList(Merchandise merch) {
        model.addProductToList(merch);
    }

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
        List<Order> allOrders = this.getOrders();
        this.view.printAllOrders(allOrders);
    }

    public void updateViewAllCars() {
        List<Car> allCars = this.getAllCars();
        this.view.printAllCars(allCars);
    }

    public void updateViewAllParts() {
        List<Part> allParts = this.getAllParts();
        this.view.printAllParts(allParts);
    }

    public void updateViewPendingOrder() {
        List<Merchandise> buyCart = this.viewPendingOrder();
        this.view.printPendingOrder(buyCart);
    }

    public void updateViewMoney() {
        double money = this.getMoney();
        this.view.printMoney(money);
    }

    public void updateViewPartsForACar(int ID) {
        List<Part> parts = this.getAllPartsForACar(ID);
        this.view.printAllParts(parts);
    }

    public void updateViewCarsForAPart(int ID) {
        List<Car> cars = this.getAllCarsForAPart(ID);
        this.view.printAllCars(cars);
    }

    public void updateViewMinCar(){
        Car car = this.findMin();
        this.view.printMinCar(car);
    }
}
