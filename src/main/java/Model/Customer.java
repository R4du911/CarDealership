package Model;

import Errors.CustomIllegalArgument;
import Interface.CustomerSystem;
import Model.Repo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends Person implements CustomerSystem {
    private final List<Order> orders;
    private Double money;
    private final ProductList pendingOrder;
    private Inventory inMemoInventory;

    public Customer(String user, String passwd, String firstName, String lastName, Double money, Inventory inventory) {
        super(user, passwd, firstName, lastName);
        this.orders = new ArrayList<>();
        this.money = money;
        this.inMemoInventory = inventory;
        this.pendingOrder = new ProductList();
    }

    public void setInventory(Inventory inv){
        this.inMemoInventory = inv;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Double getMoney() {return money;}

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();

        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch instanceof Car) {
                cars.add((Car) merch);
            }
        }

        return cars;
    }

    public List<Part> getAllParts() {
        List<Part> parts = new ArrayList<>();
        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch instanceof Part) {
                parts.add((Part) merch);
            }
        }
        return parts;
    }

    public void addOrder(Date date) throws CustomIllegalArgument {
        Double sumPrice = 0.0;

        if (this.pendingOrder.getPurchased().isEmpty()) {
            throw new CustomIllegalArgument("ProductList is empty");
        }

        for (Merchandise merch : this.pendingOrder.getPurchased()) {
            sumPrice += merch.getPrice();
            if (sumPrice > this.getMoney()) {
                throw new CustomIllegalArgument("Not enough money");
            }
        }

        for (Merchandise merch : this.pendingOrder.getPurchased()) {
            this.inMemoInventory.remove_Merch(merch.getID());
        }

        this.setMoney(this.getMoney() - sumPrice);

        ProductList boughtMerch = new ProductList();
        boughtMerch.setPurchased(this.pendingOrder.getPurchased());
        this.orders.add(new Order(boughtMerch, date));

        this.pendingOrder.setPurchased(new ArrayList<>());


    }

    public void addProductToList(int ID) throws CustomIllegalArgument{
        for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
            if(merch.getID() == ID){
                this.pendingOrder.addProductToList(merch);
                return;
            }
        }
        throw new CustomIllegalArgument("Product does not exist");

    }

    public void removeProductFromList(int ID) throws CustomIllegalArgument{
        boolean found = false;
        for(Merchandise product : this.pendingOrder.getPurchased()) {
            if (product.getID() == ID) {
                found = true;
                this.pendingOrder.removeProductFromList(ID);
                break;
            }
        }
        if(!found){
            throw new CustomIllegalArgument("Product does not exist in pendingOrder");
        }
    }

    public List<Merchandise> viewPendingOrder() {
        return this.pendingOrder.getPurchased();
    }

    public List<Part> getAllPartsForACar(int ID) throws CustomIllegalArgument {
        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch.getID() == ID && merch instanceof Car) {
                Car car = (Car) merch;
                return car.getUsableParts();
            }
        }
        throw new CustomIllegalArgument("Car does not exist");
    }

    public List<Car> getAllCarsForAPart(int ID) throws CustomIllegalArgument {
        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch.getID() == ID && merch instanceof Part) {
                Part part = (Part) merch;
                return part.getForCars();
            }
        }
        throw new CustomIllegalArgument("Part does not exist");
    }
}
