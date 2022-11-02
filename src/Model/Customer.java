package Model;
import Interface.CustomerSystem;
import Model.Repo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends Person implements CustomerSystem {
    private final List<Order> orders;
    private Double money;
    private final ProductList pendingOrder;
    private final InMemoInventory inMemoInventory;

    public Customer(String user, String passwd, String firstName, String lastName, Double money, InMemoInventory inventory) {
        super(user, passwd, firstName, lastName);
        this.orders = new ArrayList<>();
        this.money = money;
        this.inMemoInventory = inventory;
        this.pendingOrder = new ProductList();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<Car>();

        for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
            if(merch instanceof Car){
                cars.add((Car) merch);
            }
        }

        return cars;
    }

    public List<Part> getAllParts() {
        List<Part> parts  = new ArrayList<>();
        for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
            if(merch instanceof Part){
                parts.add((Part) merch);
            }
        }
        return parts;
    }

    public void addOrder(Date date) throws IllegalArgumentException, ArithmeticException{
        Double sumPrice = 0.0;

        if(this.pendingOrder.getPurchased().isEmpty()){
            throw new IllegalArgumentException("ProductList is empty");
        }

        for(Merchandise merch : this.pendingOrder.getPurchased()){
                if (!this.inMemoInventory.getCarsAndParts().contains(merch)) {
                    throw new IllegalArgumentException("Product does not exist");
                } else {
                    sumPrice += merch.getPrice();
                    if (sumPrice > this.getMoney()) {
                        throw new ArithmeticException("Not enough money");
                    }
                }
        }

        for(Merchandise merch : this.pendingOrder.getPurchased()) {
            this.inMemoInventory.remove_Merch(merch);
        }

        this.setMoney(this.getMoney() - sumPrice);

        ProductList boughtMerch = new ProductList();
        boughtMerch.setPurchased(this.pendingOrder.getPurchased());
        this.orders.add(new Order(boughtMerch,date));

        this.pendingOrder.setPurchased(new ArrayList<>());


    }

    public void addProductToList(Merchandise merch){
        this.pendingOrder.addProductToList(merch);
    }

    public void removeProductFromList(Merchandise merch){
        this.pendingOrder.removeProductFromList(merch);
    }

    public List<Merchandise> viewPendingOrder(){
        return this.pendingOrder.getPurchased();
    }

    @Override
    public boolean checkStatus() {
        return false;
    }
}
