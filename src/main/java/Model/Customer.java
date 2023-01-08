package Model;

import Errors.CustomIllegalArgument;
import Interface.CustomerSystem;
import Model.Repo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends Person implements CustomerSystem {
    private List<Order> orders;
    private Double money;
    private final ProductList pendingOrder;
    private Inventory inMemoInventory;

    public Customer(String user, String passwd, String firstName, String lastName, Double money, Inventory inventory) {
        super(user, passwd, firstName, lastName);
        this.orders = new ArrayList<>();
        this.money = money;
        this.inMemoInventory = inventory;
        this.pendingOrder = new ProductList();
    }

    public void setInventory(Inventory inv){
        this.inMemoInventory = inv;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders){
        this.orders = orders;
    }

    public Double getMoney() {return money;}

    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     * @return all cars from the merch-repository
     */
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
     * @return all parts from the merch-repository
     */
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
     * @param date when the order was placed
     * @throws CustomIllegalArgument if the customer's shopping basket is empty, or if the user does not have enough money
     * The order is placed, the selected merch are bought and saved locally to the customer's order list and the customer's shopping list is emptied
     */
    public void addOrder(Date date) throws CustomIllegalArgument {
        Double sumPrice = 0.0;

        if (this.pendingOrder.getPurchased().isEmpty()) {
            throw new CustomIllegalArgument("ProductList is empty");
        }

        for (Merchandise merch : this.pendingOrder.getPurchased()) {
            sumPrice += merch.getPrice();
            if (sumPrice > this.getMoney()) {
                throw new CustomIllegalArgument("Not enough money");
            }
        }

        this.setMoney(this.getMoney() - sumPrice);

        ProductList boughtMerch = new ProductList();
        boughtMerch.setPurchased(this.pendingOrder.getPurchased());
        this.orders.add(new Order(boughtMerch, date));

        this.pendingOrder.setPurchased(new ArrayList<>());
    }

    /**
     *  @param date when the order was placed
     *  @throws CustomIllegalArgument if the customer's shopping basket is empty, or if the user does not have enough money
     *  The order is placed, the selected merch are bought and saved in the database in the customer's order list and the customer's
     *  shopping list is emptied
     */
    public void addOrderToDatabase(Date date) throws CustomIllegalArgument{
        Double sumPrice = 0.0;
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        if (this.pendingOrder.getPurchased().isEmpty()) {
            throw new CustomIllegalArgument("ProductList is empty");
        }

        for (Merchandise merch : this.pendingOrder.getPurchased()) {
            sumPrice += merch.getPrice();
            if (sumPrice > this.getMoney()) {
                throw new CustomIllegalArgument("Not enough money");
            }
        }

        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            String sqlUpdateOrder = "INSERT INTO Orders (Customerusername,Datum) VALUES (" + "'" + this.getUser() + "'" + ", " + "'" + sqlDate + "'" + ")";
            statement.executeUpdate(sqlUpdateOrder);

            String sqlGetOrderID = "SELECT ID FROM Orders WHERE Customerusername = " + "'" + this.getUser() + "'" + " AND Datum = " + "'" + sqlDate + "'";
            resultSet = statement.executeQuery(sqlGetOrderID);
            int OrderID = 0;
            while (resultSet.next()) {
                OrderID = resultSet.getInt(1);
            }

            String sqlUpdateProductList = "INSERT INTO ProductLists (IDOrder) VALUES (" + "'" + OrderID + "'" + ")";
            statement.executeUpdate(sqlUpdateProductList);

            String sqlGetProductListID = "SELECT ID FROM ProductLists WHERE IDOrder = " + OrderID;
            resultSet = statement.executeQuery(sqlGetProductListID);
            int ProductListID = 0;
            while(resultSet.next()){
                ProductListID = resultSet.getInt(1);
            }

            for(Merchandise merch : this.pendingOrder.getPurchased()){
                String sqlBoughtItems  = "INSERT INTO Products_ProductLists (IDProdList,IDProd) VALUES (" + "'" + ProductListID + "'" + ", " + "'" + merch.getID() + "'" + ")";
                statement.executeUpdate(sqlBoughtItems);
                this.removeProductFromShoppingListDatabase(merch.getID());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * The orders of each customer are being saved locally from database
     */
    public void populateOrderList(){
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

        ResultSet resultSetOrder;
        ResultSet resultSetProdList;
        ResultSet resultSetBoughtProducts;
        this.setOrders(new ArrayList<>());

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            String sqlPopulateOrder = "SELECT ID,Datum FROM Orders WHERE Customerusername = " + "'" + this.getUser() + "'";
            resultSetOrder = statement.executeQuery(sqlPopulateOrder);
            List<Integer> OrderIDs = new ArrayList<>();
            List<Date> OrderDates = new ArrayList<>();

            while(resultSetOrder.next()){
                OrderIDs.add(resultSetOrder.getInt(1));
                OrderDates.add(new Date(resultSetOrder.getDate(2).getTime()));
            }


            int counter = 0;
            while (counter < OrderIDs.size()) {
                List<Merchandise> products = new ArrayList<>();
                String sqlPopulateProductList = "SELECT ID FROM ProductLists WHERE IDOrder = " + OrderIDs.get(counter);
                Date date = OrderDates.get(counter);
                resultSetProdList = statement.executeQuery(sqlPopulateProductList);

                if(resultSetProdList.next()){
                    String sqlPopulateBoughtProd = "SELECT IDProd FROM Products_ProductLists WHERE IDProdList = " + resultSetProdList.getInt(1);
                    resultSetBoughtProducts = statement.executeQuery(sqlPopulateBoughtProd);

                    while (resultSetBoughtProducts.next()) {
                        for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
                            if(merch.getID() == resultSetBoughtProducts.getInt(1)){
                                products.add(merch);
                            }
                        }
                    }
                }

                ProductList productList = new ProductList();
                productList.setPurchased(products);
                Order order = new Order(productList,date);
                this.orders.add(order);
                counter++;
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param ID - of the merch to be added to the shopping list
     * @throws CustomIllegalArgument if the given ID is not found
     * Adds a merch to the customer's locally saved shopping list
     */
    public void addProductToList(int ID) throws CustomIllegalArgument{
        for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
            if(merch.getID() == ID){
                this.pendingOrder.addProductToList(merch);
                return;
            }
        }
        throw new CustomIllegalArgument("Product does not exist");

    }

    /**
     * @param IDProd - of the merch to be added to the shopping list
     * @throws CustomIllegalArgument if the given ID is not found
     * Adds a merch in the database to the customer's shopping list
     */
    public void addProductToShoppingListDatabase(int IDProd) {
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/


        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            String sqlUpdatePart = "INSERT INTO Products_ShoppingLists (CustomerShoppingList,IDProduct) VALUES (" + "'" + this.getUser() + "'" + ", " + "'" + IDProd + "'" + ")";
            statement.executeUpdate(sqlUpdatePart);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The shopping lists of each customer are being saved locally from the database
     */
    public void populateShoppingList(){
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            String sqlPopulateSL = "SELECT IDProduct FROM Products_ShoppingLists WHERE CustomerShoppingList = " + "'" + this.getUser() + "'";
            resultSet = statement.executeQuery(sqlPopulateSL);

            List<Merchandise> products = new ArrayList<>();
            while (resultSet.next()) {
                for(Merchandise merch : this.inMemoInventory.getCarsAndParts()){
                    if(merch.getID() == resultSet.getInt(1)){
                        products.add(merch);
                    }
                }
            }

            this.pendingOrder.setPurchased(products);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param ID of the merch to be removed from the shopping list
     * @throws CustomIllegalArgument if the given ID is not found
     * Removes a merch from the customer's locally saved shopping list
     */
    public void removeProductFromList(int ID) throws CustomIllegalArgument{
        boolean found = false;
        for(Merchandise product : this.pendingOrder.getPurchased()) {
            if (product.getID() == ID) {
                found = true;
                this.pendingOrder.removeProductFromList(ID);
                break;
            }
        }
        if(!found){
            throw new CustomIllegalArgument("Product does not exist in pendingOrder");
        }
    }

    /**
     * @param IDProd of the merch to be removed from the shopping list
     * @throws CustomIllegalArgument if the given ID is not found
     * Removes a merch from the database from teh customer's shopping list
     */
    public void removeProductFromShoppingListDatabase(int IDProd) {
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/


        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            String sqlUpdatePart = "DELETE FROM Products_ShoppingLists WHERE CustomerShoppingList = " + "'" + this.getUser() + "'" + "AND IDProduct = " + "'" + IDProd + "'";
            statement.executeUpdate(sqlUpdatePart);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return all items that are selected in the customer's shopping list
     */
    public List<Merchandise> viewPendingOrder() {
        return this.pendingOrder.getPurchased();
    }

    /**
     * @param ID of the car
     * @return all parts that can be used on the given car
     * @throws CustomIllegalArgument if the given ID is not found
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
     * @return all cars for which the part can be used
     * @throws CustomIllegalArgument if the given ID is not found
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
