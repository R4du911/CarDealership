import Controller.*;
import Interface.RegisterLogin;
import Model.*;
import Model.Repo.Inventory;
import Model.Repo.UserRepo;
import View.CustomerView;
import View.SalespersonView;

import java.sql.*;
import java.util.*;
import java.util.Date;

import static java.lang.System.exit;

public class Menu implements RegisterLogin {
    private Controller controller;
    private Inventory inventory;
    private final UserRepo userRepo = new UserRepo();

    @Override
    public void login(int savingOption) throws IllegalArgumentException {
        this.populateUserRepo();

        Scanner console = new Scanner(System.in);
        System.out.println("Your user name: ");
        String user = console.nextLine();

        System.out.println("Your password: ");
        String passwd = console.nextLine();

        if (userRepo.getUsers().isEmpty()) {
            System.out.println("User does not exist, do you want to register: ");
            String answer = console.nextLine();

            if (answer.equals("ja") || answer.equals("Ja")) {
                this.register(savingOption);
            }
        }

        boolean found = false;
        for (Person userSaved : userRepo.getUsers()) {

            if (userSaved.getUser().equals(user) && !userSaved.getPasswd().equals(passwd)) {
                throw new IllegalArgumentException("Wrong password");
            }

            if (userSaved.getUser().equals(user) && userSaved.getPasswd().equals(passwd)) {
                found = true;
                if (userSaved instanceof Customer) {
                    this.inventory = new Inventory();
                    CustomerView view = new CustomerView();
                    ((Customer) userSaved).setInventory(this.inventory);
                    this.controller = new CustomerController((Customer) userSaved, view);

                    if (savingOption == 1)
                        this.populateInMemory();
                    if (savingOption == 2)
                        this.populateInventoryFromDatabase();

                    this.menu(savingOption);
                } else {
                    this.inventory = new Inventory();
                    SalespersonView view = new SalespersonView();
                    ((Salesperson) userSaved).setInventory(this.inventory);
                    this.controller = new SalespersonController((Salesperson) userSaved, view);

                    if (savingOption == 1)
                        this.populateInMemory();
                    if (savingOption == 2)
                        this.populateInventoryFromDatabase();

                    this.menu(savingOption);
                }
            }
        }

        if (!found) {
            System.out.println("User does not exist, do you want to register: ");
            String answer = console.nextLine();

            if (answer.equals("ja") || answer.equals("Ja")) {
                this.register(savingOption);
            }
        }

    }

    @Override
    public void register(int savingOption) throws IllegalArgumentException {
        this.populateUserRepo();

        Scanner console = new Scanner(System.in);
        System.out.println("You want to register as an: ");
        String type = console.nextLine();

        if (!type.equals("Customer") && !type.equals("customer") && !type.equals("Salesperson") && !type.equals("salesperson")) {
            throw new IllegalArgumentException("Type does not exist");
        }

        if (type.equals("Salesperson") || type.equals("salesperson")) {
            System.out.println("Password for Salesperson registration:");
            if (!console.nextLine().equals("admin")) {
                System.out.println("Wrong password. Consider trying again");
                exit(0);
            }
        }

        System.out.println("Your user name: ");
        String user = console.nextLine();

        for (Person userSaved : userRepo.getUsers()) {
            if (userSaved.getUser().equals(user)) {
                throw new IllegalArgumentException("User already exist. Consider logging in");
            }
        }

        System.out.println("Your password: ");
        String passwd = console.nextLine();

        System.out.println("Your first name: ");
        String firstName = console.nextLine();

        System.out.println("Your last Name: ");
        String lastName = console.nextLine();

        if (type.equals("Customer") || type.equals("customer")) {
            this.inventory = new Inventory();

            if (savingOption == 1)
                this.populateInMemory();
            if (savingOption == 2)
                this.populateInventoryFromDatabase();

            Customer customer = new Customer(user, passwd, firstName, lastName, 25000.0, inventory);
            userRepo.addUser(customer);
            this.insertUser(type, user, passwd, firstName, lastName);

            CustomerView view = new CustomerView();
            this.controller = new CustomerController(customer, view);
            this.menu(savingOption);
        }

        if (type.equals("Salesperson") || type.equals("salesperson")) {
            this.inventory = new Inventory();

            if (savingOption == 1)
                this.populateInMemory();
            if (savingOption == 2)
                this.populateInventoryFromDatabase();

            Salesperson salesperson = new Salesperson(user, passwd, firstName, lastName, 1300.0, inventory);
            userRepo.addUser(salesperson);
            this.insertUser(type, user, passwd, firstName, lastName);

            SalespersonView view = new SalespersonView();
            this.controller = new SalespersonController(salesperson, view);
            this.menu(savingOption);
        }
    }

    public void menu(int savingOption) {
        if (controller instanceof CustomerController) {

            //menu for Customer...
            System.out.println("\n");
            System.out.println("Options: ");
            System.out.println("[1]-Add product to shopping list ");//add prod to shopping list
            System.out.println("[2]-Remove product from shopping list");//remove prod from shopping list
            System.out.println("[3]-Place the order(products from shopping list)");//updateViewAllOrders
            System.out.println("[4]-View all products from shopping list");//updateViewPendingOrder
            System.out.println("[5]-Show all car options for purchase");//updateViewAllCars
            System.out.println("[6]-Show all part options for purchase");//updateViewAllParts
            System.out.println("[7]-See how much money you have");//updateViewMoney
            System.out.println("[8]-Show the cheapest car available");//updateViewMinCar
            System.out.println("[[9]]-Show all orders that you have made");//updateViewAllOrders
            System.out.println("[10]-Show all usable parts for a car");//updateViewPartsForACar
            System.out.println("[11]-Show all cars that match a part");//updateViewCarsForAPart
            System.out.println("<<Press 0 key for log out>>");
            System.out.println("\n");

            System.out.println("Choose an option");
            Scanner console = new Scanner(System.in).useLocale(Locale.US);
            int option = console.nextInt();
            System.out.println("\n");

            switch (option) {
                case 1:
                    System.out.println("What ID has the product you want to add?");
                    int IDProduct = console.nextInt();

                    ((CustomerController) this.controller).addProductToList(IDProduct);

                    if (savingOption == 2)
                        ((CustomerController) this.controller).addProductToShoppingListDatabase(IDProduct);

                    this.menu(savingOption);
                case 2:
                    System.out.println("What ID has the product you want to remove?");
                    int IDProductRemove = console.nextInt();

                    if (savingOption == 2)
                        ((CustomerController) this.controller).removeProductFromShoppingListDatabase(IDProductRemove);

                    ((CustomerController) this.controller).removeProductFromList(IDProductRemove);

                    this.menu(savingOption);
                case 3:
                    Date date = new Date();

                    if (savingOption == 2)
                        ((CustomerController) this.controller).addOrderToDatabase(date);

                    ((CustomerController) this.controller).addOrder(date);

                    this.menu(savingOption);
                case 4:
                    if(savingOption == 2)
                        ((CustomerController) this.controller).populateShoppingList();

                    ((CustomerController) this.controller).updateViewPendingOrder();

                    this.menu(savingOption);
                case 5:
                    ((CustomerController) this.controller).updateViewAllCars();
                    this.menu(savingOption);
                case 6:
                    ((CustomerController) this.controller).updateViewAllParts();
                    this.menu(savingOption);
                case 7:
                    ((CustomerController) this.controller).updateViewMoney();
                    this.menu(savingOption);
                case 8:
                    ((CustomerController) this.controller).updateViewMinCar();
                    this.menu(savingOption);
                case 9:
                    if(savingOption == 2) {
                        ((CustomerController) this.controller).populateOrderList();
                    }

                    ((CustomerController) this.controller).updateViewAllOrders();

                    this.menu(savingOption);
                case 10:
                    System.out.println("What ID has the car?");
                    ((CustomerController) this.controller).updateViewPartsForACar(console.nextInt());
                    this.menu(savingOption);
                case 11:
                    System.out.println("What ID has the part?");
                    ((CustomerController) this.controller).updateViewCarsForAPart(console.nextInt());
                    this.menu(savingOption);
                case 0:
                    exit(0);
                default:
                    System.out.println("Wrong input...try a value from 0 to 11");
                    this.menu(savingOption);
            }


        } else {

            //menu for Salesperson...
            System.out.println("\n");
            System.out.println("Options: ");
            System.out.println("[1]-Add a product to inventory");//add
            System.out.println("[2]-Remove a product from inventory");//remove
            System.out.println("[3]-Update a product from inventory");//update
            System.out.println("[4]-View all cars from inventory");//updateViewAllCars
            System.out.println("[5]-View all parts from inventory");//updateViewAllParts
            System.out.println("[6]-Show your salary");//updateViewSalary
            System.out.println("[7]-Show all usable parts for a car");//updateViewPartsForACar
            System.out.println("[8]-Show all cars that match a part");//updateViewCarsForAPart
            System.out.println("[9]-Show cheaper cars than given price");//updateViewFilterAllCarsByPrice
            System.out.println("<<Press 0 key for log out>>");
            System.out.println("\n");

            System.out.println("Choose an option");
            Scanner console = new Scanner(System.in).useLocale(Locale.US);
            int option = console.nextInt();
            System.out.println("\n");

            switch (option) {
                case 1:
                    console.nextLine();

                    System.out.println("Do you want to add a car or a part to inventory?");
                    String type = console.nextLine();

                    if (type.equals("Car") || type.equals("car")) {
                        System.out.println("Enter car ID:");
                        int id = console.nextInt();
                        console.nextLine();
                        System.out.println("Enter car brand:");
                        String brand = console.nextLine();
                        System.out.println("Enter car model:");
                        String model = console.nextLine();
                        System.out.println("Enter car price:");
                        Double price = console.nextDouble();
                        System.out.println("Enter car year of registration:");
                        int yearOfReg = console.nextInt();
                        console.nextLine();
                        System.out.println("Enter fuel type:");
                        String motor = console.nextLine();

                        System.out.println("\n");
                        List<Part> parts = new ArrayList<>();
                        List<Part> allParts = ((SalespersonController) this.controller).getAllParts();
                        System.out.println("Choose from the following parts the ones that can be used on this vehicle:");
                        System.out.println("Type 0 when you finished adding all the parts");
                        ((SalespersonController) this.controller).updateViewAllParts();
                        option = console.nextInt();

                        while (option != 0) {
                            boolean found = false;
                            for (Part part : allParts) {
                                if (part.getID() == option) {
                                    found = true;
                                    if (!parts.contains(part))
                                        parts.add(part);
                                    else {
                                        System.out.println("Part is already selected");
                                    }
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.println("Part not found in warehouse");
                            }
                            option = console.nextInt();
                        }

                        Car car = new Car(id, brand, model, price, yearOfReg, motor, parts);
                        ((SalespersonController) this.controller).add(car);

                        for (Part part : car.getUsableParts()) {
                            List<Car> carsForPart = part.getForCars();
                            carsForPart.add(car);
                            part.setForCars(carsForPart);
                        }

                        //save to database
                        if (savingOption == 2) {
                            ((SalespersonController) this.controller).insertNewCarDatabase(id, brand, model, price, yearOfReg, motor, parts);
                        }

                    } else if (type.equals("Part") || type.equals("part")) {
                        System.out.println("Enter part ID:");
                        int id = console.nextInt();
                        console.nextLine();
                        System.out.println("Enter part brand:");
                        String brand = console.nextLine();
                        System.out.println("Enter part model:");
                        String model = console.nextLine();
                        System.out.println("Enter part price:");
                        Double price = console.nextDouble();

                        System.out.println("\n");
                        List<Car> cars = new ArrayList<>();
                        List<Car> allCars = ((SalespersonController) this.controller).getAllCars();
                        System.out.println("Choose from the following cars the ones that can be used on this part:");
                        System.out.println("Type 0 when you finished adding all the cars");
                        ((SalespersonController) this.controller).updateViewAllCars();
                        option = console.nextInt();

                        while (option != 0) {
                            boolean found = false;
                            for (Car car : allCars) {
                                if (car.getID() == option) {
                                    found = true;
                                    if (!cars.contains(car))
                                        cars.add(car);
                                    else {
                                        System.out.println("Car is already selected");
                                    }
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.println("Car not found in warehouse");
                            }
                            option = console.nextInt();
                        }

                        Part part = new Part(id, brand, model, price, cars);
                        ((SalespersonController) this.controller).add(part);

                        for (Car car : part.getForCars()) {
                            List<Part> partForCars = car.getUsableParts();
                            partForCars.add(part);
                            car.setUsableParts(partForCars);
                        }

                        //save to database
                        if (savingOption == 2) {
                            ((SalespersonController) this.controller).insertNewPartDatabase(id, brand, model, price, cars);
                        }

                    } else {
                        System.out.println("Invalid type. Please try again");
                    }

                    this.menu(savingOption);
                case 2:
                    System.out.println("What ID has the product you want to remove from inventory?");
                    int ID = console.nextInt();

                    //save to database
                    if (savingOption == 2)
                        ((SalespersonController) this.controller).deleteProductDatabase(ID);

                    ((SalespersonController) this.controller).remove(ID);

                    this.menu(savingOption);
                case 3:
                    System.out.println("What ID has the product you want to update");

                    int id = console.nextInt();

                    boolean found = false;
                    Merchandise product;
                    for (Merchandise merch : this.inventory.getCarsAndParts()) {
                        if (merch.getID() == id) {
                            found = true;
                            console.nextLine();
                            product = merch;
                            System.out.println("What category do you want to update:");
                            String category = console.nextLine();

                            if (category.equals("Price") || category.equals("price")) {
                                System.out.println("New price:");
                                double newPrice = console.nextDouble();
                                product.setPrice(newPrice);

                                //save to database
                                if (savingOption == 2) {
                                    ((SalespersonController) this.controller).updatePriceDatabase(id,newPrice);
                                }
                            }

                            if (merch instanceof Car && (category.equals("Add parts") || category.equals("add parts"))) {
                                List<Part> newParts = new ArrayList<>();
                                List<Part> parts = ((Car) merch).getUsableParts();
                                List<Part> allParts = ((SalespersonController) this.controller).getAllParts();
                                System.out.println("Choose from the following parts the ones that you want to add for this vehicle:");
                                System.out.println("Type 0 when you finished adding all the parts");
                                ((SalespersonController) this.controller).updateViewAllParts();
                                option = console.nextInt();

                                while (option != 0) {
                                    boolean foundProduct = false;
                                    for (Part part : allParts) {
                                        if (part.getID() == option) {
                                            foundProduct = true;
                                            if (!parts.contains(part)) {
                                                newParts.add(part);
                                                parts.add(part);
                                                List<Car> cars = part.getForCars();
                                                cars.add((Car) merch);
                                                part.setForCars(cars);
                                            } else {
                                                System.out.println("Part is already selected");
                                            }
                                            break;
                                        }
                                    }
                                    if (!foundProduct) {
                                        System.out.println("Part not found in warehouse");
                                    }
                                    option = console.nextInt();
                                }

                                ((Car) product).setUsableParts(parts);

                                //save to database
                                if (savingOption == 2) {
                                    ((SalespersonController) this.controller).updatePartsForCarDatabase(id,newParts);
                                }
                            }
                            if (merch instanceof Part && (category.equals("Add cars") || category.equals("add cars"))) {
                                List<Car> newCars = new ArrayList<>();
                                List<Car> cars = ((Part) merch).getForCars();
                                List<Car> allCars = ((SalespersonController) this.controller).getAllCars();
                                System.out.println("Choose from the following cars the ones that you want to add to this part:");
                                System.out.println("Type 0 when you finished adding all the cars");
                                ((SalespersonController) this.controller).updateViewAllCars();
                                option = console.nextInt();

                                while (option != 0) {
                                    boolean foundProduct = false;
                                    for (Car car : allCars) {
                                        if (car.getID() == option) {
                                            foundProduct = true;
                                            if (!cars.contains(car)) {
                                                newCars.add(car);
                                                cars.add(car);
                                                List<Part> parts = car.getUsableParts();
                                                parts.add((Part) merch);
                                                car.setUsableParts(parts);
                                            } else {
                                                System.out.println("Car is already in the list");
                                            }
                                            break;
                                        }
                                    }
                                    if (!foundProduct) {
                                        System.out.println("Car not found in warehouse");
                                    }
                                    option = console.nextInt();
                                }

                                ((Part) product).setForCars(cars);

                                //save to database
                                if (savingOption == 2) {
                                    ((SalespersonController) this.controller).updateCarsForPartDatabase(id,newCars);
                                }
                            }
                            ((SalespersonController) this.controller).update(product);
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Product not found in warehouse. Consider trying again");
                    }

                    this.menu(savingOption);
                case 4:
                    ((SalespersonController) this.controller).updateViewAllCars();
                    this.menu(savingOption);
                case 5:
                    ((SalespersonController) this.controller).updateViewAllParts();
                    this.menu(savingOption);
                case 6:
                    ((SalespersonController) this.controller).updateViewSalary();
                    this.menu(savingOption);
                case 7:
                    System.out.println("What ID has the car?");
                    ((SalespersonController) this.controller).updateViewPartsForACar(console.nextInt());
                    this.menu(savingOption);
                case 8:
                    System.out.println("What ID has the part?");
                    ((SalespersonController) this.controller).updateViewCarsForAPart(console.nextInt());
                    this.menu(savingOption);
                case 9:
                    System.out.println("What is the highest price you want a car to have ?");
                    ((SalespersonController) this.controller).updateViewFilterAllCarsByPrice(console.nextInt());
                    this.menu(savingOption);
                case 0:
                    exit(0);
                default:
                    System.out.println("Wrong input...try a value from 0 to 9");
                    this.menu(savingOption);

            }
        }
    }

    public void populateInMemory() {
        List<Part> parts = new ArrayList<>();
        Car car1 = new Car(1, "Ford", "Fiesta", 2225.0, 2005, "Diesel", parts);
        Car car2 = new Car(2, "Ford", "Focus", 1230.0, 2010, "Diesel", parts);

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        Part part1 = new Part(3, "Toyota", "XCH-I", 34.5, cars);
        parts.add(part1);
        car1.setUsableParts(parts);
        car2.setUsableParts(parts);

        this.inventory.add_Merch(car1);
        this.inventory.add_Merch(car2);
        this.inventory.add_Merch(part1);

        Car car3 = new Car(4, "Skoda", "Octavia", 3550.2, 2011, "Diesel", parts);
        cars.add(car3);
        part1.setForCars(cars);

        List<Part> parts2 = new ArrayList<>();
        List<Car> cars2 = new ArrayList<>();

        cars2.add(car3);
        Part part2 = new Part(5, "Fuchs", "T70-P", 57.2, cars2);
        parts2.add(part1);
        parts2.add(part2);
        car3.setUsableParts(parts2);

        this.inventory.add_Merch(car3);
        this.inventory.add_Merch(part2);

    }

    public void populateInventoryFromDatabase() {
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM Cars INNER JOIN Inventory ON Cars.ID = Inventory.ID";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                this.inventory.add_Merch(new Car(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getDouble(4), resultSet.getInt(5), resultSet.getString(6), new ArrayList<>()));
            }

            String sql2 = "SELECT * FROM Parts INNER JOIN Inventory ON Parts.ID = Inventory.ID";
            resultSet = statement.executeQuery(sql2);
            while (resultSet.next()) {
                this.inventory.add_Merch(new Part(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getDouble(4), new ArrayList<>()));
            }

            for (Merchandise merch : this.inventory.getCarsAndParts()) {
                if (merch instanceof Car) {
                    List<Part> parts = new ArrayList<>();
                    String sqlParts = "SELECT PartID FROM CarPartDependency WHERE CarID = " + merch.getID();
                    resultSet = statement.executeQuery(sqlParts);

                    while (resultSet.next()) {
                        for (Merchandise prod : this.inventory.getCarsAndParts()) {
                            if (prod.getID() == resultSet.getInt(1)) {
                                parts.add((Part) prod);
                                break;
                            }
                        }
                    }
                    ((Car) merch).setUsableParts(parts);
                }

                if (merch instanceof Part) {
                    List<Car> cars = new ArrayList<>();
                    String sqlCars = "SELECT CarID FROM CarPartDependency WHERE PartID = " + merch.getID();
                    resultSet = statement.executeQuery(sqlCars);

                    while (resultSet.next()) {
                        for (Merchandise prod : this.inventory.getCarsAndParts()) {
                            if (prod.getID() == resultSet.getInt(1)) {
                                cars.add((Car) prod);
                                break;
                            }
                        }
                    }
                    ((Part) merch).setForCars(cars);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void populateUserRepo() {
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM Customers INNER JOIN UserRepo ON Customers.username = UserRepo.username";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                this.userRepo.addUser(new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDouble(5), this.inventory));
            }

            String sql2 = "SELECT * FROM Salespersons INNER JOIN UserRepo ON Salespersons.username = UserRepo.username";
            resultSet = statement.executeQuery(sql2);
            while (resultSet.next()) {
                this.userRepo.addUser(new Salesperson(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDouble(5), this.inventory));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void insertUser(String type, String user, String pass, String firstName, String lastName) {
        String url = "jdbc:sqlserver://DESKTOP-GRAUEBQ\\SQLEXPRESS:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "radu";
        String password = "1234";

        /*String url = "jdbc:sqlserver://UBB-L33\\SQLEXPRESS01:1433;database=CarDealership;encrypt=true;trustServerCertificate=true;loginTimeout=30";
        String userName = "tudor";
        String password = "cardeal";*/

        try (Connection connection = DriverManager.getConnection(url, userName, password); Statement statement = connection.createStatement()) {
            if (type.equals("Customer") || type.equals("customer")) {
                String sqlUserRepo = "INSERT INTO UserRepo (username) Values(" + "'" + user + "'" + ")";
                statement.executeUpdate(sqlUserRepo);

                String sqlCustomers = "INSERT INTO Customers (username,password,firstname,lastname,money) VALUES (" + "'" + user + "'" + "," + "'" + pass + "'" + "," +
                        "'" + firstName + "'" + "," + "'" + lastName + "'" + ",25000.0)";
                statement.executeUpdate(sqlCustomers);

                String sqlShoppingList =  "INSERT INTO ShoppingLists (Customerusername) VALUES (" + "'" + user + "'" + ")";
                statement.executeUpdate(sqlShoppingList);
            }
            if (type.equals("Salesperson") || type.equals("salesperson")) {
                String sqlUserRepo = "INSERT INTO UserRepo Values(" + "'" + user + "'" + ")";
                statement.executeUpdate(sqlUserRepo);

                String sqlSalesperson = "INSERT INTO Salespersons VALUES (" + "'" + user + "'" + "," + "'" + pass + "'" + "," + "'" + firstName + "'" + "," +
                        "'" + lastName + "'" + ",1300.0)";
                statement.executeUpdate(sqlSalesperson);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
