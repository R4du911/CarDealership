import Errors.CustomIllegalArgument;

import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws CustomIllegalArgument {
        Menu menu = new Menu();
        Scanner console = new Scanner(System.in);

        System.out.println("Persistent or volatile save?");
        System.out.println("[1]-volatile");
        System.out.println("[2]-persistent");
        System.out.println("What is your option?");
        int savingOption = console.nextInt();

        System.out.println("Login or register?");
        System.out.println("[1]-Login");
        System.out.println("[2]-Register");

        System.out.println("\n" + "What is your option?");
        switch (console.nextInt()) {
            case 1 -> menu.login(savingOption);
            case 2 -> menu.register(savingOption);
            default -> {
                System.out.println("Not a valid option, please try again");
                exit(0);
            }
        }
    }
}