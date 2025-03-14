package website.ylab.financetracker.adapter.in.budget;

import java.util.Scanner;

public class BudgetCLI implements BudgetInterface{
    private final Scanner scanner;

    public BudgetCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public double getLimit() {
        double limit=0.0;
        do {
            try {
                limit = Double.parseDouble(getData(
                        "Enter a new budget constraint. It must be greater than zero."));
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Invalid amount");
            }
        } while (limit <= 0.0);
        return limit;
    }

    private String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
