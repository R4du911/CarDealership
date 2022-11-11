import Controller.*;
import Interface.RegisterLogin;
import Model.*;
import Model.Repo.InMemoInventory;
import Model.Repo.UserRepo;
import View.CustomerView;
import View.SalespersonView;

import java.util.*;

import static java.lang.System.exit;

public class Menu implements RegisterLogin {

    private Controller controller;
    private InMemoInventory inventory;
    private final UserRepo userRepo = new UserRepo();
    private Merchandise merchandise;

    @Override
    public void login() throws IllegalArgumentException {
        Scanner console = new Scanner(System.in);
        System.out.println("Your user name: ");
        String user = console.nextLine();

        System.out.println("Your password: ");
        String passwd = console.nextLine();

        if (userRepo.getUsers().isEmpty()) {
            System.out.println("User does not exist, do you want to register: ");
            String answer = console.nextLine();

            if (answer.equals("ja")) {
                this.register();
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
                    this.inventory = new InMemoInventory();
                    CustomerView view = new CustomerView();
                    this.controller = new CustomerController((Customer) userSaved, view);
                    this.populateInMemory();
                    this.menu();
                } else {
                    this.inventory = new InMemoInventory();
                    SalespersonView view = new SalespersonView();
                    this.controller = new SalespersonController((Salesperson) userSaved, view);
                    this.populateInMemory();
                    this.menu();
                }
            }
        }

        if (!found) {
            System.out.println("User does not exist, do you want to register: ");
            String answer = console.nextLine();

            if (answer.equals("ja")) {
                this.register();
            }
        }

    }

    @Override
    public void register() throws IllegalArgumentException {
        Scanner console = new Scanner(System.in);
        System.out.println("You want to register as an: ");
        String type = console.nextLine();

        if (!type.equals("Customer") && !type.equals("Salesperson")) {
            throw new IllegalArgumentException("Type does not exist");
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

        if (type.equals("Customer")) {
            this.inventory = new InMemoInventory();
            this.populateInMemory();
            Customer customer = new Customer(user, passwd, firstName, lastName, 25000.0, inventory);
            userRepo.addUser(customer);
            CustomerView view = new CustomerView();
            this.controller = new CustomerController(customer, view);
            this.menu();
        }

        if (type.equals("Salesperson")) {
            this.inventory = new InMemoInventory();
            this.populateInMemory();
            Salesperson salesperson = new Salesperson(user, passwd, firstName, lastName, 1300.0, inventory);
            userRepo.addUser(salesperson);
            SalespersonView view = new SalespersonView();
            this.controller = new SalespersonController(salesperson, view);
            this.menu();
        }
    }

    public void menu() {

        //customer menu
        if (controller instanceof CustomerController) {

            //menu for Customer...
            System.out.println("Options: ");
            System.out.println("[1]-Add product to shopping list ");
            System.out.println("[2]-Remove product from shopping list");
            System.out.println("[3]-Place the order(products from shopping list)");
            System.out.println("[4]-View all products from shopping list");//updateViewPendingOrder
            System.out.println("[5]-Show all car options for purchase");//updateViewAllCars
            System.out.println("[6]-Show all part options for purchase");//updateViewAllParts
            System.out.println("[7]-See how much money you have");//updateViewMoney
            System.out.println("[8]-Show the cheapest car available");//updateViewMinCar
            System.out.println("[[9]]-Show all orders that you have made");//updateViewAllOrders
            System.out.println("[10]-Show all usable parts for a car");//updateViewPartsForACar
            System.out.println("[11]-Show all cars that match a part");//updateViewCarsForAPart
            System.out.println("<<Press 0 key for log out>>");

            System.out.println("Choose an option");
            Scanner console = new Scanner(System.in);
            int option = console.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Do you want to add a car or a part");
                    this.menu();
                case 2:
                    System.out.println("What ID has the product you want to remove?");
                    ((CustomerController) this.controller).removeProductFromList(console.nextInt());
                    this.menu();
                case 3:
                    Date date = new Date();
                    ((CustomerController) this.controller).addOrder(date);
                    this.menu();
                case 4:
                    ((CustomerController) this.controller).updateViewPendingOrder();
                    this.menu();
                case 5:
                    ((CustomerController) this.controller).updateViewAllCars();
                    this.menu();
                case 6:
                    ((CustomerController) this.controller).updateViewAllParts();
                    this.menu();
                case 7:
                    ((CustomerController) this.controller).updateViewMoney();
                    this.menu();
                case 8:
                    ((CustomerController) this.controller).updateViewMinCar();
                    this.menu();
                case 9:
                    ((CustomerController) this.controller).updateViewAllOrders();
                    this.menu();
                case 10:
                    System.out.println("What ID has the car?");
                    ((CustomerController) this.controller).updateViewPartsForACar(console.nextInt());
                    this.menu();
                case 11:
                    System.out.println("What ID has the part?");
                    ((CustomerController) this.controller).updateViewCarsForAPart(console.nextInt());
                    this.menu();
                case 0:
                    exit(0);
                default:
                    System.out.println("Wrong input...try a value from 0 to 11");
                    this.menu();
            }


        }

        //salesperson menu
        else {

            //menu for Salesperson...
            System.out.println("[1]-Add a product to inventory");//add
            System.out.println("[2]-Remove a product from inventory");//remove
            System.out.println("[3]-Update a product from inventory");//update
            System.out.println("[4]-View all cars from inventory");//updateViewAllCars
            System.out.println("[5]-View all parts from inventory");//updateViewAllParts
            System.out.println("[6]-Show your salary");//updateViewSalary
            System.out.println("[7]-Show all usable parts for a car");//updateViewPartsForACar
            System.out.println("[8]-Show all cars that match a part");//updateViewCarsForAPart
            System.out.println("[9]-Filter cars by given price");//updateViewFilterAllCarsByPrice
            System.out.println("<<Press 0 key for log out>>");//updateViewFilterAllCarsByPrice

            System.out.println("Choose an option");
            Scanner console = new Scanner(System.in);
            int option = console.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Do you want to add a car or a part to inventory?");
                    //logic for  add merchandise
                    this.menu();
                case 2:
                    System.out.println("What ID has the product you want to remove from inventory?");
                    ((SalespersonController) this.controller).remove(console.nextInt());
                    this.menu();
                case 3:
                    System.out.println("Do you want to update a car o a part?");
                    if (Objects.equals(console.nextLine(), "car") && Objects.equals(console.nextLine(), "Car")) {
                       /* this.inventory = new InMemoInventory();
                        this.populateInMemory();
                        System.out.println("Enter car ID:");
                        int id= console.nextInt();
                        System.out.println("Enter car brand:");
                        String brand= console.nextLine();
                        System.out.println("Enter car model:");
                        String model= console.nextLine();
                        System.out.println("Enter car price:");
                        Double price= console.nextDouble();
                        System.out.println("Enter car year of registration:");
                        String yearOfReg= console.nextLine();
                        Car car = new Car(id,brand,model,price,yearOfReg);//List<Part> parts);
                        ((SalespersonController) this.controller).update(car);
                        this.menu();
                        */
                    }
                    if (Objects.equals(console.nextLine(), "part") && Objects.equals(console.nextLine(), "Part")) {
                        /* this.inventory = new InMemoInventory();
                        this.populateInMemory();
                        System.out.println("Enter part ID:");
                        int id= console.nextInt();
                        System.out.println("Enter car brand:");
                        String brand= console.nextLine();
                        System.out.println("Enter car model:");
                        String model= console.nextLine();
                        System.out.println("Enter car price:");
                        Double price= console.nextDouble();
                        System.out.println("Enter car year of registration:");
                        String yearOfReg= console.nextLine();
                        Part part = new Part(id,brand,model,price);//List<Car> cars);
                        ((SalespersonController) this.controller).update(part);
                        this.menu();
                        */
                    }

                case 4:
                    ((SalespersonController) this.controller).updateViewAllCars();
                    this.menu();
                case 5:
                    ((SalespersonController) this.controller).updateViewAllParts();
                    this.menu();
                case 6:
                    ((SalespersonController) this.controller).updateViewSalary();
                    this.menu();
                case 7:
                    System.out.println("What ID has the car?");
                    ((SalespersonController) this.controller).updateViewPartsForACar(console.nextInt());
                    this.menu();
                case 8:
                    System.out.println("What ID has the part?");
                    ((SalespersonController) this.controller).updateViewCarsForAPart(console.nextInt());
                    this.menu();
                case 9:
                    System.out.println("What is the highest price you want a car to have ?");
                    ((SalespersonController) this.controller).updateViewFilterAllCarsByPrice(console.nextInt());
                    this.menu();
                case 0:
                    exit(0);
                default:
                    System.out.println("Wrong input...try a value from 0 to 9");
                    this.menu();

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
    }
}
