package website.ylab.financetracker.adapter.in.target;

import java.util.Scanner;

public class TargetCLI implements TargetInterface{
    private final Scanner scanner;

    public TargetCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public double getTargetAmount() {
        double target=0;
        do {
            try {
                target = Double.parseDouble(getData(
                        "Enter a new target's amount. It must be greater than zero."));
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Invalid amount");
            }
        } while (target <= 0);
        return target;
    }

    private String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
