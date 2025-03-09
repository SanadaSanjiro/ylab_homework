package website.ylab.financetracker.auth;

import website.ylab.financetracker.ServiceProvider;

import java.util.Scanner;

/**
 * Prompts the user for login information
 */
public class LoginDataInput {
    public static String login() {
        Scanner scanner = new Scanner(System.in);
        String name = getName(scanner);
        String password = getPassword(scanner);
        UserAuthService authService = ServiceProvider.getUserAuthService();
        return authService.login(name, password);
    }

    public static String logout() {
        UserAuthService.logout();
        return "Current user logged out";
    }

    private static String getName(Scanner scanner) {
        System.out.println("Please enter your name.");
        return scanner.nextLine();
    }

    private static String getPassword(Scanner scanner) {
        System.out.println("Please enter your password.");
        return scanner.nextLine();
    }
}