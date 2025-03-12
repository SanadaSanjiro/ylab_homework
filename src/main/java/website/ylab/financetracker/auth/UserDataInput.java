package website.ylab.financetracker.auth;

import website.ylab.financetracker.ServiceProvider;

import java.util.Objects;
import java.util.Scanner;

/**
 * Prompts the user for information required for account operations
 */
public class UserDataInput {

    /**
     * Requests the user to provide the data necessary to register in the system
     * @return Results of the operation to register a new user in the system in string
     */
    public static String createNewUser() {
        Scanner scanner = new Scanner(System.in);
        String name = getName(scanner, "Please enter your name. It must be between 1 and 20 characters long" +
                " and contain only Latin letters and numbers. ");
        String email = getEmail(scanner, "Please enter your e-mail.");
        String password = getPassword(scanner, "Please enter your password. " +
                "It must be between 1 and 20 characters");
        UserRegistrationService userRegService = ServiceProvider.getUserRegistrationService();
        return userRegService.addNewUser(name, email, password);
    }

    public static String changeUserData() {
        TrackerUser oldUser = UserAuthService.getCurrentUser();
        if (Objects.isNull(oldUser)) {
            return "To change user data you must be logged in.";
        }
        Scanner scanner = new Scanner(System.in);
        String name = getName(scanner, """
                Please enter new user name.
                It must be between 1 and 20 characters long and contain only Latin letters and numbers.""");
        String email = getEmail(scanner, "Please enter new e-mail.");
        String password = getPassword(scanner, """
                Please enter your new password.
                It must be between 1 and 20 characters""");
        TrackerUser user = new TrackerUser(name, email, password);
        UserService userService = ServiceProvider.getUserService();
        return userService.changeUser(user);
    }

    public static String deleteUser() {
        TrackerUser user = UserAuthService.getCurrentUser();
        if (Objects.isNull(user)) {
            return "You must be logged in to delete your user.";
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("You are about to delete your user. This will result in the loss of all saved data. " +
                "Are you sure? (y/n)");
        while (true) {
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                UserService userService = ServiceProvider.getUserService();
                return userService.deleteCurrentUser();
            }
            if (answer.equalsIgnoreCase("n")) {
                return "Operation cancelled";
            } else {
                System.out.println("Error: illegal input");
            }
        }
    }

    private static String getName(Scanner scanner, String message) {
        System.out.println(message);
        String name;
        boolean checkOk;
        do {
            name = scanner.nextLine();
            checkOk=UserDataVerificator.isValidName(name);
            if (!checkOk) {
                System.out.println("Please enter a valid name.");
            }
        } while(!checkOk);
        return name;
    }

    private static String getEmail(Scanner scanner, String message) {
        System.out.println(message);
        String email;
        boolean checkOk;
        do {
            email = scanner.nextLine();
            checkOk=UserDataVerificator.isValidEmail(email);
            if (!checkOk) {
                System.out.println("Please enter a valid e-mail.");
            }
        } while(!checkOk);
        return email;
    }

    private static String getPassword(Scanner scanner, String message) {
        System.out.println(message);
        String password;
        boolean checkOk;
        do {
            password = scanner.nextLine();
            if (password.isEmpty() || password.length() > 20) {
                System.out.println("Please enter a correct password.");
                checkOk=false;
            } else {
                checkOk = true;
            }
        } while(!checkOk);
        return password;
    }
}