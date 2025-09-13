package tech.zeta;

import tech.zeta.exception.InvalidPasswordException;
import tech.zeta.exception.InvalidUserException;
import tech.zeta.menu.*;
import tech.zeta.model.User;
import tech.zeta.service.UserService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);
        String option="Yes";

        do {
            System.out.println("<<<<<<<<<<<<<<< Payments Management System >>>>>>>>>>>>>");
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            try {
                User loggedInUser = userService.login(email, password);
                if (loggedInUser != null) {
                    System.out.println("Login successful! Welcome ");

                    switch (loggedInUser.getRole()) {  // if role is a String
                        case "admin":
                            new AdminMenu().show(loggedInUser);
                            break;
                        case "finance manager":
                            new FinanceManagerMenu().show(loggedInUser);
                            break;
                        case "viewer":
                            new ViewerMenu().show();
                            break;
                        default:
                            System.out.println("Invalid role assigned.");
                    }
                }
            } catch (InvalidUserException | InvalidPasswordException exception) {
                System.err.println(exception.getMessage());
            }
            System.out.println("Enter Yes to continue : ");
            option = scanner.nextLine();
        }while(option.equals("Yes"));
    }
}
