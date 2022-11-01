package Model;
import Model.Repo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends Person{
    private final List<Order> orders;
    private Double money;
    private final InMemoInventory inMemoInventory;

    public Customer(String user, String passwd, String firstName, String lastName, List<Order> orders, Double money, InMemoInventory inventory) {
        super(user, passwd, firstName, lastName);
        this.orders = orders;
        this.money = money;
        this.inMemoInventory = inventory;
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
        List<Part> parts  = new ArrayList<Part>();
        for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
            if(merch instanceof Part){
                parts.add((Part) merch);
            }
        }
        return parts;
    }

    public void addOrder(ProductList products, Date date) throws IllegalArgumentException, ArithmeticException{
        Double sumPrice = 0.0;

        for(Merchandise merch : products.getPurchased()){
            try {
                if (!this.inMemoInventory.getCarsAndParts().contains(merch)) {
                    throw new IllegalArgumentException();
                } else {
                    sumPrice += merch.getPrice();
                    if (sumPrice > this.getMoney()) {
                        throw new ArithmeticException();
                    }
                }
            }catch(IllegalArgumentException err){
                System.out.println("Product does not exist");
            }catch(ArithmeticException err2){
                System.out.println("Not enough money");
            }
        }

        this.setMoney(this.getMoney() - sumPrice);
        this.orders.add(new Order(products,date));
    }

    @Override
    public boolean checkStatus() {
        return false;
    }
}
