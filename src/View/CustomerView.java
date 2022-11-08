package View;

import Model.Car;
import Model.Merchandise;
import Model.Order;
import Model.Part;

import java.util.List;

public class CustomerView {
    public void printAllOrders(List<Order> allOrders) {
        for (Order order : allOrders) {
            System.out.println(order.toStringOrder() + '\n');
        }
    }

    public void printAllCars(List<Car> allCars) {
        for (Car car : allCars) {
            System.out.println(car.toStringCar() + "\n");
        }
    }

    public void printAllParts(List<Part> allParts) {
        for (Part part : allParts) {
            System.out.println(part.toStringPart() + "\n");
        }
    }

    public void printPendingOrder(List<Merchandise> buyCart) {
        for (Merchandise product : buyCart) {
            if (product instanceof Car) {
                System.out.println(((Car) product).toStringCar() + "\n");
            } else {
                System.out.println(((Part) product).toStringPart() + "\n");
            }
        }
    }

    public void printMoney(double money) {
        System.out.println("Money: " + money + "\n");
    }
}
