import Controller.*;
import Interface.RegisterLogin;
import Model.*;
import Model.Repo.InMemoInventory;
import Model.Repo.UserRepo;
import View.CustomerView;
import View.SalespersonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu implements RegisterLogin {

    private Controller controller;
    private InMemoInventory inventory;
    private final UserRepo userRepo = new UserRepo();

    @Override
    public void login() throws IllegalArgumentException{
        Scanner console = new Scanner(System.in);
        System.out.println("Your user name: ");
        String user = console.nextLine();

        System.out.println("Your password: ");
        String passwd = console.nextLine();

        if(userRepo.getUsers().isEmpty()){
            System.out.println("User does not exist, do you want to register: ");
            String answer = console.nextLine();

            if(answer.equals("ja")){
                this.register();
            }
        }

        boolean found = false;
        for(Person userSaved : userRepo.getUsers()){

            if(userSaved.getUser().equals(user) && !userSaved.getPasswd().equals(passwd)){
                throw new IllegalArgumentException("Wrong password");
            }

            if(userSaved.getUser().equals(user) && userSaved.getPasswd().equals(passwd)){
                found = true;
                if(userSaved instanceof Customer){
                    this.inventory = new InMemoInventory();
                    CustomerView view = new CustomerView();
                    this.controller = new CustomerController((Customer) userSaved,view);
                    this.menu();
                }
                else{
                    this.inventory = new InMemoInventory();
                    SalespersonView view = new SalespersonView();
                    this.controller = new SalespersonController((Salesperson) userSaved, view);
                    this.menu();
                }
            }
        }

        if(!found) {
            System.out.println("User does not exist, do you want to register: ");
            String answer = console.nextLine();

            if (answer.equals("ja")) {
                this.register();
            }
        }

    }

    @Override
    public void register() throws IllegalArgumentException{
        Scanner console = new Scanner(System.in);
        System.out.println("You want to register as an: ");
        String type = console.nextLine();

        if(!type.equals("Customer") && !type.equals("Salesperson")){
            throw new IllegalArgumentException("Type does not exist");
        }

        System.out.println("Your user name: ");
        String user = console.nextLine();

        for(Person userSaved : userRepo.getUsers()){
            if(userSaved.getUser().equals(user)){
                throw new IllegalArgumentException("User already exist. Consider logging in");
            }
        }

        System.out.println("Your password: ");
        String passwd = console.nextLine();

        System.out.println("Your first name: ");
        String firstName = console.nextLine();

        System.out.println("Your last Name: ");
        String lastName = console.nextLine();

        if(type.equals("Customer")){
            this.inventory = new InMemoInventory();
            this.populateInMemory();
            Customer customer = new Customer(user,passwd,firstName,lastName,25000.0,inventory);
            userRepo.addUser(customer);
            CustomerView view = new CustomerView();
            this.controller = new CustomerController(customer,view);
            this.menu();
        }

        if(type.equals("Salesperson")) {
            this.inventory = new InMemoInventory();
            this.populateInMemory();
            Salesperson salesperson = new Salesperson(user, passwd, firstName, lastName, 1300.0, inventory);
            userRepo.addUser(salesperson);
            SalespersonView view = new SalespersonView();
            this.controller = new SalespersonController(salesperson, view);
            this.menu();
        }
    }

    public void menu(){

        //customer menu
        if(controller instanceof CustomerController){
            CustomerController customerController;
            customerController = (CustomerController) this.controller;

            //menu for Customer...
        }

        //salesperson menu
        else{
            SalespersonController salespersonController;
            salespersonController = (SalespersonController) this.controller;

            //menu for Salesperson...
        }
    }

    public void populateInMemory(){
        List<Part> parts = new ArrayList<>();
        Car car1 = new Car(1, "Ford", "Fiesta", 2225.0, 2005, "Diesel", parts);
        Car car2 = new Car(2, "Ford", "Focus", 1230.0, 2010, "Diesel", parts);

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        Part part1 = new Part(3,"Toyota", "XCH-I", 34.5, cars);
        parts.add(part1);
        car1.setUsableParts(parts);
        car2.setUsableParts(parts);

        this.inventory.add_Merch(car1);
        this.inventory.add_Merch(car2);
        this.inventory.add_Merch(part1);
    }
}
