package View;

import Model.Car;
import Model.Merchandise;
import Model.Order;
import Model.Part;

import java.util.List;

public class CustomerView {
    /**
     * @param allOrders order list
     * prints all the orders from the order list
     */
    public void printAllOrders(List<Order> allOrders) {
        if(allOrders.isEmpty()){
            System.out.println("No orders found!" + "\n");
            return;
        }
        for (Order order : allOrders) {
            System.out.println(order.toStringOrder());
        }
        System.out.println("\n");
    }

    /**
     * @param allCars car list
     * prints all the cars from the car list
     */
    public void printAllCars(List<Car> allCars) {
        if(allCars.isEmpty()){
            System.out.print("No cars found!");
            System.out.println("We are sorry for the inconvenience" + "\n");
            return;
        }
        for (Car car : allCars) {
            System.out.println(car.toStringCar());
        }
        System.out.println("\n");
    }

    /**
     * @param allParts part list
     * prints all parts from the part list
     */
    public void printAllParts(List<Part> allParts) {
        if(allParts.isEmpty()){
            System.out.print("No parts found!");
            System.out.println("We are sorry for the inconvenience" + "\n");
            return;
        }
        for (Part part : allParts) {
            System.out.println(part.toStringPart());
        }
        System.out.println("\n");
    }

    /**
     * @param buyCart shopping list
     * prints all merch from one's shopping list
     */
    public void printPendingOrder(List<Merchandise> buyCart) {
        if(buyCart.isEmpty()){
            System.out.println("Your basket is empty" + "\n");
            return;
        }
        for (Merchandise product : buyCart) {
            if (product instanceof Car) {
                System.out.println(((Car) product).toStringCar());
            } else {
                System.out.println(((Part) product).toStringPart());
            }
        }
        System.out.println("\n");
    }

    /**
     * @param money of a customer
     * prints the remaining money that a customer has
     */
    public void printMoney(double money) {
        System.out.println("Money: " + money + "\n");
    }

    /**
     * @param car cheapest car
     * print the cheapest car
     */
    public void printMinCar(Car car){System.out.println("Cheapest car: " + car.toStringCar() + "\n");}
}
