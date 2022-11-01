package test;

import Controller.CustomerController;
import Model.*;
import Model.Repo.InMemoInventory;
import View.CustomerView;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerTest {
    InMemoInventory inventory = new InMemoInventory();
    List<Order> orders = new ArrayList<>();
    Customer customer = new Customer("Admin", "admin", "Costel", "Marculescu", orders, 10000.0, inventory);
    CustomerView view;
    CustomerController customerCtrl = new CustomerController(customer, view);
    List<Part> parts = new ArrayList<>();
    List<Car> cars = new ArrayList<>();

    @Test
    void addOrder() {
        Car car1 = new Car(1, "Volvo", "xc60", 5500.0, 2010, "Diesel", parts);
        Car car2 = new Car(2, "Mercedes", "c-class", 3550.0, 2015, "Diesel", parts);
        Part part1 = new Part(3, "Toyota", "XC-235", 234.0, cars);
        inventory.add_Merch(car1);
        inventory.add_Merch(part1);
        inventory.add_Merch(car2);

        ProductList products = new ProductList();
        List<Merchandise> selectedProducts = new ArrayList<>();
        selectedProducts.add(car1);
        selectedProducts.add(part1);
        products.setPurchased(selectedProducts);
        Date date = new Date(2022,Calendar.NOVEMBER,1);

        customerCtrl.addOrder(products,date);
        assert(customerCtrl.getOrders().get(0).getProductList().getPurchased().contains(car1));
        assert(customerCtrl.getOrders().get(0).getProductList().getPurchased().contains(part1));
        assert(!customerCtrl.getOrders().get(0).getProductList().getPurchased().contains(car2));
        assert(!customerCtrl.getAllCars().contains(car1));

        System.out.println("Add order works good...");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customerCtrl.addOrder(products,date));
        assertEquals("Product does not exist", exception.getMessage());

        Car car3 = new Car(4, "Volvo", "xc60", 15500.0, 2014, "Gasoline", parts);
        inventory.add_Merch(car3);
        List<Merchandise> selectedProducts2 = new ArrayList<>();
        selectedProducts2.add(car3);
        products.setPurchased(selectedProducts2);
        ArithmeticException exception2 = assertThrows(ArithmeticException.class, () -> customerCtrl.addOrder(products,date));
        assertEquals("Not enough money", exception2.getMessage());

    }
}