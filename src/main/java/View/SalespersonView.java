package View;

import Model.*;

import java.util.List;

public class SalespersonView {


    /**
     * @param allCars car list
     * prints all cars from the car list
     */
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

    /**
     * @param allParts part list
     * prints all parts from the part list
     */
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

    /**
     * @param salary of a salesperson
     * prints the salary of a salesperson
     */
    public void printSalary(double salary) {
        System.out.println("Salary: " + salary);
    }
}
