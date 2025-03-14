package website.ylab.financetracker.adapter.in.transaction;

import website.ylab.financetracker.transactions.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

public class TransactionCLI implements TransactionInterface {
    private final Scanner scanner;

    public TransactionCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public TransactionType getType() {
        Optional<TransactionType> typeOptional;
        do {
            typeOptional = TransactionType.fromString(
                    getData("Enter the transaction type (INCOME or EXPENSE)"));
            if (typeOptional.isEmpty()) {
                System.out.println("Invalid transaction type");
            }
        } while (typeOptional.isEmpty());
        return typeOptional.get();
    }

    @Override
    public double getAmount() {
        double amount=0;
        do {
            try {
                String input = getData("Enter the amount. It must be greater than zero.");
                amount = Double.parseDouble(input);
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Invalid amount");
            }
        } while (amount <= 0);
        return amount;
    }

    @Override
    public String getCategory() {
        String category;
        do {
            category = getData("Enter the category. " +
                    "It must not be empty and must be longer than 32 characters.");
            if (category.isEmpty() || category.length() > 32) {
                System.out.println("Invalid category");
            }
        } while (category.isBlank() || category.length() > 32);
        return category;
    }

    @Override
    public Date getDate() {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        boolean isParseOk = false;
        String input;
        do {
            input = getData("Enter the date (dd.MM.yyyy): ");
            try {
                date=formatter.parse(input);
                isParseOk = true;
            } catch (ParseException e) {
                System.out.println("Invalid date");
            }
        } while (!isParseOk);
        return date;
    }

    @Override
    public String getDescription() {
        String description;
        do {
            description = getData("Enter the description. " +
                    "It must not be empty and must be longer than 256 characters.");
            if (description.isEmpty() || description.length() > 256) {
                System.out.println("Invalid description");
            }
        } while (description.isBlank() || description.length() > 256);
        return description;
    }

    @Override
    public long getId() {
        long id=0;
        boolean isParseOk = false;
        do {
            try {
                id = Long.parseLong(getData("Enter transaction id."));
                isParseOk = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid transaction id");
            }
        } while (!isParseOk);
        return id;
    }

    @Override
    public String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}