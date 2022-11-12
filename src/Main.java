import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        System.out.println("Login or register?");
        System.out.println("[1]-Login");
        System.out.println("[2]-Register");

        System.out.println("\n" + "What is your option?");
        Scanner console = new Scanner(System.in);
        switch (console.nextInt()) {
            case 1 -> menu.login();
            case 2 -> menu.register();
            default -> {
                System.out.println("Not a valid option, please try again");
                exit(0);
            }
        }
    }
}