package website.ylab.financetracker.adapter.in.auth;

import java.util.Scanner;

public class UserCLI implements UserInterface {
    private final Scanner scanner;
    private final UserVerifierInterface dataVerifier;

    public UserCLI(Scanner scanner, UserVerifierInterface dataVerifier) {
        this.scanner = scanner;
        this.dataVerifier = dataVerifier;
    }

    @Override
    public String getName() {
        String name;
        boolean checkOk;
        do {
            name = getData("Please enter user name.\n" +
                    "It must be between 1 and 20 characters long and contain only Latin letters and numbers.");
            checkOk= dataVerifier.isValidName(name);
            if (!checkOk) {
                System.out.println("Please enter a valid name.");
            }
        } while(!checkOk);
        return name;
    }

    @Override
    public String getEmail() {
        String email;
        boolean checkOk;
        do {
            email = getData("Please enter e-mail.");
            checkOk= dataVerifier.isValidEmail(email);
            if (!checkOk) {
                System.out.println("Please enter a valid e-mail.");
            }
        } while(!checkOk);
        return email;
    }

    @Override
    public String getPassword() {
        String password;
        boolean checkOk;
        do {
            password = getData("Please enter password. It must be between 1 and 20 characters");
            if (password.isEmpty() || password.length() > 20) {
                System.out.println("Please enter a correct password.");
                checkOk=false;
            } else {
                checkOk = true;
            }
        } while(!checkOk);
        return password;
    }

    @Override
    public String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}