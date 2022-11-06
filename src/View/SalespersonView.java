package View;

import Model.*;

import java.util.List;

public class SalespersonView {

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

    public void printSalary(double salary) {
        System.out.println("Salary: " + salary + "\n");
    }
}
