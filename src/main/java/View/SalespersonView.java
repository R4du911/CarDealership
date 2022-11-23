package View;

import Model.*;

import java.util.List;

public class SalespersonView {

    public void printAllCars(List<Car> allCars) {
        if(allCars.isEmpty()){
            System.out.print("No cars found!" + "\n");
            System.out.println("We are sorry for the inconvenience" + "\n");
            return;
        }
        for (Car car : allCars) {
            System.out.println(car.toStringCar());
        }
        System.out.println("\n");
    }

    public void printAllParts(List<Part> allParts) {
        if(allParts.isEmpty()){
            System.out.print("No parts found!" + "\n");
            System.out.println("We are sorry for the inconvenience" + "\n");
            return;
        }
        for (Part part : allParts) {
            System.out.println(part.toStringPart());
        }
    }

    public void printSalary(double salary) {
        System.out.println("Salary: " + salary);
    }
}
