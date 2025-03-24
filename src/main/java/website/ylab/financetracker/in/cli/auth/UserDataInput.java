package website.ylab.financetracker.in.cli.auth;

import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserDataVerificator;
import website.ylab.financetracker.service.auth.UserService;

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
        UserService userService = ServiceProvider.getUserService();
        Scanner scanner = new Scanner(System.in);
        String message = "Please enter your name. It must be between 1 and 20 characters long" +
                " and contain only Latin letters and numbers.";
        String name = getName(scanner, message, userService);
        message = "Please enter your e-mail.";
        String email = getEmail(scanner, message, userService);
        String password = getPassword(scanner, "Please enter your password. " +
                "It must be between 1 and 20 characters");
        return userService.addNewUser(new TrackerUser()
                .setUsername(name)
                .setEmail(email)
                .setPassword(password)).toString();
    }

    public static String changeUserData() {
        UserService userService = ServiceProvider.getUserService();
        long userId = UserAuthService.getCurrentUserId();
        if (userId==0L) {
            return "To change user data you must be logged in.";
        }
        Scanner scanner = new Scanner(System.in);
        String message = """
                Please enter new user name.
                It must be between 1 and 20 characters long and contain only Latin letters and numbers.""";
        String name = getName(scanner, message, userService);
        message = "Please enter new e-mail.";
        String email = getEmail(scanner, message, userService);
        String password = getPassword(scanner, """
                Please enter your new password.
                It must be between 1 and 20 characters""");
        TrackerUser user = new TrackerUser()
                .setUsername(name)
                .setEmail(email)
                .setPassword(password);
        long id = userService.getByName(name).getId();
        user.setId(id);
        return userService.changeUser(user).toString();
    }

    public static String deleteUser() {
        long userId = UserAuthService.getCurrentUserId();
        if (userId==0L) {
            return "You must be logged in to delete your user.";
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("You are about to delete your user. This will result in the loss of all saved data. " +
                "Are you sure? (y/n)");
        while (true) {
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                UserService userService = ServiceProvider.getUserService();
                return userService.deleteUser(userId).toString();
            }
            if (answer.equalsIgnoreCase("n")) {
                return "Operation cancelled";
            } else {
                System.out.println("Error: illegal input");
            }
        }
    }

    private static String getName(Scanner scanner, String message, UserService userService) {
        System.out.println(message);
        String name;
        boolean checkOk;
        do {
            name = scanner.nextLine();
            checkOk= UserDataVerificator.isValidName(name);
            if (!checkOk) {
                System.out.println("Please enter a valid name.");
            }
        } while(!checkOk);
        return name;
    }

    private static String getEmail(Scanner scanner, String message, UserService userService) {
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