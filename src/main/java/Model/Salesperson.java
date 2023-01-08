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

    /**
     * @param merch to be added to the merch-repository
     * @throws CustomIllegalArgument if the merch is already stored in the merch-repository
     * Adds a new merch locally to the merch-repository
     */
    @Override
    public void add(Merchandise merch) throws CustomIllegalArgument{
        for(Merchandise product : this.inMemoInventory.getCarsAndParts()){
            if(product.getID() == merch.getID()){
                throw new CustomIllegalArgument("Product with same ID already in warehouse");
            }
        }
        this.inMemoInventory.add_Merch(merch);
    }

    /**
     * @param id of the new car
     * @param brand of the new car
     * @param model of the new car
     * @param price of the new car
     * @param yearOfReg of the new car
     * @param motor of the new car
     * @param parts of the new car
     * Adds a new car in the database of the merch-repository
     */
    public void insertNewCar(int id, String brand, String model, double price, int yearOfReg, String motor, List<Part> parts) {
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

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

    /**
     * @param id of the new part
     * @param brand of the new part
     * @param model of the new part
     * @param price of the new part
     * @param cars of the new car
     * Adds a new part in the database of the merch-repository
     */
    public void insertNewPart(int id, String brand, String model, double price, List<Car> cars) {
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

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


    /**
     * @param ID of the merch to be removed
     * @throws CustomIllegalArgument if the ID is not found
     * Removes a merch from the locally merch-repository
     */
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

    /**
     * @param id of the merch to be removed
     * Removes a merch in the database of the merch-repository
     */
    public void deleteProduct(int id){
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

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

    /**
     * @param merch to be updated
     * @throws CustomIllegalArgument if the merch is not found
     * Updates a given merch in the locally merch-repository
     */
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

    /**
     * @param id of the merch to be updated
     * @param newPrice of the merch to be updated
     * Updates the price of the given merch in the database of the merch-repository
     */
    public void updatePrice(int id, Double newPrice){
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

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

    /**
     * @param id of the car to be updated
     * @param parts to be added to the given car
     * Updates the parts for a given car in the database of the merch-repository
     */
    public void updatePartsForCar(int id, List<Part> parts){
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

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


    /**
     * @param id of the part to be updated
     * @param cars to be added to the given part
     * Updates the cars for a given part in the database of the merch-repository
     */
    public void updateCarsForPart(int id, List<Car> cars){
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

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

    /**
     * @return all the cars of the merch-repository
     */
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

    /**
     * @return all the parts of the merch-repository
     */
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

    /**
     * @param ID of the car
     * @return all the parts that can be used for the given car
     * @throws CustomIllegalArgument if the car is not found
     */
    public List<Part> getAllPartsForACar(int ID) throws CustomIllegalArgument {
        for (Merchandise merch : this.inMemoInventory.getCarsAndParts()) {
            if (merch.getID() == ID && merch instanceof Car) {
                Car car = (Car) merch;
                return car.getUsableParts();
            }
        }
        throw new CustomIllegalArgument("Car does not exist");
    }

    /**
     * @param ID of the part
     * @return all the cars on which the given part can be used
     * @throws CustomIllegalArgument if the part is not found
     */
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


