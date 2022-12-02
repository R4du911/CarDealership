package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Errors.CustomIllegalArgument;
import Model.Repo.*;
import Interface.*;

public class Salesperson extends Person implements DealershipSystem {
    private final double salary;
    private Inventory inMemoInventory;

    public Salesperson(String user, String passwd, String firstName, String lastName, double salary, Inventory inventory) {
        super(user, passwd, firstName, lastName);
        this.salary = salary;
        this.inMemoInventory = inventory;
    }

    public void setInventory(Inventory inv){
        this.inMemoInventory = inv;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public void add(Merchandise merch) throws CustomIllegalArgument{
        for(Merchandise product : this.inMemoInventory.getCarsAndParts()){
            if(product.getID() == merch.getID()){
                throw new CustomIllegalArgument("Product with same ID already in warehouse");
            }
        }
        this.inMemoInventory.add_Merch(merch);
    }

    public void insertNewCar(int id, String brand, String model, double price, int yearOfReg, String motor, List<Part> parts) {
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            String sqlAddToInv = "INSERT INTO Inventory (ID) VALUES (" + "'" + id + "'" + ")";
            statement.executeUpdate(sqlAddToInv);

            String sqlAddCar = "INSERT INTO Cars (ID,Brand,Model,Price,YearOfReg,Motor) VALUES (" + "'" + id + "'" + "," + "'" + brand + "'" + "," +
                    "'" + model + "'" + "," + "'" + price + "'" + "," + "'" + yearOfReg + "'" + "," + "'" + motor + "'" + ")";
            statement.executeUpdate(sqlAddCar);

            for (Part part : parts) {
                String sqlAddCarPartDependent = "INSERT INTO CarPartDependency (CarID, PartID) VALUES (" + "'" + id + "'" + "," + "'"
                        + part.getID() + "'" + ")";
                statement.executeUpdate(sqlAddCarPartDependent);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertNewPart(int id, String brand, String model, double price, List<Car> cars) {
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            String sqlAddToInv = "INSERT INTO Inventory (ID) VALUES (" + "'" + id + "'" + ")";
            statement.executeUpdate(sqlAddToInv);

            String sqlAddPart = "INSERT INTO Parts (ID,Brand,Model,Price) VALUES (" + "'" + id + "'" + "," + "'" + brand + "'" + "," +
                    "'" + model + "'" + "," + "'" + price + "'" + ")";
            statement.executeUpdate(sqlAddPart);

            for (Car car : cars) {
                String sqlAddCarPartDependent = "INSERT INTO CarPartDependency (CarID, PartID) VALUES (" + "'" + car.getID() + "'" + "," + "'"
                        + id + "'" + ")";
                statement.executeUpdate(sqlAddCarPartDependent);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(int ID) throws CustomIllegalArgument{
        boolean found = false;
        for(Merchandise product : this.inMemoInventory.getCarsAndParts()) {
            if (product.getID() == ID) {
                found = true;
                this.inMemoInventory.remove_Merch(ID);
                break;
            }
        }
        if(!found){
            throw new CustomIllegalArgument("Product does not exist");
        }
    }

    public void deleteProduct(int id){
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        String type = null;
        for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
            if(merch.getID() == id){
                if(merch instanceof Car)
                    type = "Car";
                else if (merch instanceof Part) {
                    type = "Part";
                }
                break;
            }
        }

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            if(type.equals("Car")){
                String sqlDeleteDepend = "DELETE FROM CarPartDependency WHERE CarID = " + id;
                statement.executeUpdate(sqlDeleteDepend);

                String sqlDeleteData = "DELETE FROM Cars WHERE ID = " + id;
                statement.executeUpdate(sqlDeleteData);
            }

            if(type.equals("Part")){
                String sqlDeleteDepend = "DELETE FROM CarPartDependency WHERE PartID = " + id;
                statement.executeUpdate(sqlDeleteDepend);

                String sqlDeleteData = "DELETE FROM Parts WHERE ID = " + id;
                statement.executeUpdate(sqlDeleteData);
            }

            String sqlDeleteFromInv = "DELETE FROM Inventory WHERE ID = " + id;
            statement.executeUpdate(sqlDeleteFromInv);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Merchandise merch) throws CustomIllegalArgument{
        boolean found = false;
        for (Merchandise product : this.inMemoInventory.getCarsAndParts()) {
            if (product.getID() == merch.getID()) {
                found = true;
                this.inMemoInventory.remove_Merch(product.getID());
                this.inMemoInventory.add_Merch(merch);
                break;
            }
        }
        if(!found){
            throw new CustomIllegalArgument("Product does not exist");
        }
    }

    public void updatePrice(int id, Double newPrice){
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        String type = null;
        for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
            if(merch.getID() == id){
                if(merch instanceof Car)
                    type = "Car";
                else if (merch instanceof Part) {
                    type = "Part";
                }
                break;
            }
        }

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            if(type.equals("Car")){
                String sqlUpdateCar = "UPDATE Cars SET Price = " + newPrice + " WHERE ID = " + id;
                statement.executeUpdate(sqlUpdateCar);
            }

            if(type.equals("Part")){
                String sqlUpdatePart = "UPDATE Parts SET Price = " + newPrice + " WHERE ID = " + id;
                statement.executeUpdate(sqlUpdatePart);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updatePartsForCar(int id, List<Part> parts){
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            for (Part part : parts) {
                String sqlAddCarPartDependent = "INSERT INTO CarPartDependency (CarID, PartID) VALUES (" + "'" + id + "'" + "," + "'"
                        + part.getID() + "'" + ")";
                statement.executeUpdate(sqlAddCarPartDependent);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateCarsForPart(int id, List<Car> cars){
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            for (Car car : cars) {
                String sqlAddCarPartDependent = "INSERT INTO CarPartDependency (CarID, PartID) VALUES (" + "'" + car.getID() + "'" + "," + "'"
                        + id + "'" + ")";
                statement.executeUpdate(sqlAddCarPartDependent);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();

        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch instanceof Car) {
                cars.add((Car) merch);
            }
        }
        return cars;
    }

    @Override
    public List<Part> getAllParts() {
        List<Part> parts = new ArrayList<>();
        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch instanceof Part) {
                parts.add((Part) merch);
            }
        }
        return parts;
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


