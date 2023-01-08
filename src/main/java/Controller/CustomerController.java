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

    /**
     * @return all orders that a customer has placed
     */
    public List<Order> getOrders() {
        return model.getOrders();
    }

    /**
     * @return money of a customer
     */
    public Double getMoney() {
        return model.getMoney();
    }

    /**
     * @return all cars from the merch-inventory
     */
    public List<Car> getAllCars() {
        return model.getAllCars();
    }

    /**
     * @return all parts from the inventory
     */
    public List<Part> getAllParts() {
        return model.getAllParts();
    }

    /**
     * @param date when the order was placed
     * Adds a new order to the customer's locally saved order list
     */
    public void addOrder(Date date) {
        try {
            model.addOrder(date);
        }catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * @param date when the order was placed
     * Adds a new order to the customer's database saved order list
     */
    public void addOrderToDatabase(Date date){
        try{
            model.addOrderToDatabase(date);
        }catch(Exception error){
            System.out.println(error.getMessage());
        }
    }

    /**
     * populates the order list of each customer from the database
     */
    public void populateOrderList(){
        model.populateOrderList();
    }

    /**
     * @param ID of the merch
     * Adds a new merch to the customer's locally saved shopping list
     */
    public void addProductToList(int ID) {
        try {
            model.addProductToList(ID);
        }catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * @param IDProduct of the merch
     * Adds a new merch to the customer's database saved shopping list
     */
    public void addProductToShoppingListDatabase(int IDProduct){
        model.addProductToShoppingListDatabase(IDProduct);
    }

    /**
     * @param ID of the merch
     * Removes a merch from the customer's locally saved shopping list
     */
    public void removeProductFromList(int ID) {
        try {
            model.removeProductFromList(ID);
        }catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * @param IDProd of the merch
     * Removes a merch from the customer's database saved shopping list
     */
    public void removeProductFromShoppingListDatabase(int IDProd){
        model.removeProductFromShoppingListDatabase(IDProd);
    }

    /**
     * @return the selected merch from the customer's shopping list
     */
    public List<Merchandise> viewPendingOrder() {
        return model.viewPendingOrder();
    }

    /**
     * populates the shopping list of each customer from the database
     */
    public void populateShoppingList(){
        model.populateShoppingList();
    }

    /**
     * @param ID of the car
     * @return all parts that can be used on the given car
     */
    public List<Part> getAllPartsForACar(int ID) {
        try {
            return model.getAllPartsForACar(ID);
        }catch (Exception error) {
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * @param ID of the part
     * @return all cars on which the given part can be used
     */
    public List<Car> getAllCarsForAPart(int ID) {
        try {
            return model.getAllCarsForAPart(ID);
        }catch (Exception error) {
            System.out.println(error.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * @return the cheapest car
     */
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

    /**
     * passes to the view the order list of a customer
     */
    public void updateViewAllOrders() {
        this.view.printAllOrders(this.getOrders());
    }

    /**
     * passes to the view the cars from the merch-inventory
     */
    public void updateViewAllCars() {
        this.view.printAllCars(this.getAllCars());
    }

    /**
     * passes to the view the parts from the merch-inventory
     */
    public void updateViewAllParts() {
        this.view.printAllParts(this.getAllParts());
    }

    /**
     * passes to the view the shopping list of a customer
     */
    public void updateViewPendingOrder() {
        this.view.printPendingOrder(this.viewPendingOrder());
    }

    /**
     * passes to the view the money of a customer
     */
    public void updateViewMoney() {
        this.view.printMoney(this.getMoney());
    }

    /**
     * @param ID of the car
     * passes to the view all parts that can be used on the given car
     */
    public void updateViewPartsForACar(int ID) {
        this.view.printAllParts(this.getAllPartsForACar(ID));
    }

    /**
     * @param ID of the part
     * passes to the view all cars on which the given part can be used
     */
    public void updateViewCarsForAPart(int ID) {
        this.view.printAllCars(this.getAllCarsForAPart(ID));
    }

    /**
     * passes to the view the cheapest car
     */
    public void updateViewMinCar(){
        this.view.printMinCar(this.findMin());
    }
}
