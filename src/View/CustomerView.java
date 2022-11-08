package View;

import Model.Car;
import Model.Merchandise;
import Model.Order;
import Model.Part;

import java.util.List;

public class CustomerView {
    public void printAllOrders(List<Order> allOrders) {
        for (Order order : allOrders) {
            System.out.println(order.toStringOrder());
        }
    }

    public void printAllCars(List<Car> allCars) {
        for (Car car : allCars) {
            System.out.println(car.toStringCar());
        }
    }

    public void printAllParts(List<Part> allParts) {
        for (Part part : allParts) {
            System.out.println(part.toStringPart());
        }
    }

    public void printPendingOrder(List<Merchandise> buyCart) {
        for (Merchandise product : buyCart) {
            if (product instanceof Car) {
                System.out.println(((Car) product).toStringCar());
            } else {
                System.out.println(((Part) product).toStringPart());
            }
        }
    }

    public void printMoney(double money) {
        System.out.println("Money: " + money);
    }

    public void printMinCar(Car car){System.out.println(car.toStringCar());}
}
