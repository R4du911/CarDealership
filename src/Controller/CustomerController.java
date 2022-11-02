package Controller;

import Model.*;
import View.CustomerView;

import java.util.Date;
import java.util.List;

public class CustomerController {
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

    public List<Car> getAllCars() {return model.getAllCars();}

    public List<Part> getAllParts() {return model.getAllParts();}

    public void addOrder(Date date){ model.addOrder(date);}

    public void addProductToList(Merchandise merch){ model.addProductToList(merch);}

    public void removeProductFromList(Merchandise merch){ model.removeProductFromList(merch);}

    public List<Merchandise> viewPendingOrder(){return model.viewPendingOrder();}

    public void updateViewCustomer(){
        //make view
    }
}
