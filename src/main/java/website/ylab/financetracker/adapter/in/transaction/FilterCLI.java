package website.ylab.financetracker.adapter.in.transaction;

import website.ylab.financetracker.transactions.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

public class FilterCLI implements FilterInterface{
    private final Scanner scanner;

    public FilterCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public TransactionType getTypeFilter() {
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
    public String getCategoryFilter() {
        String category;
        do {
            category = getData("Enter the category or press Enter to skip this filter.");
            if (category.isEmpty()) return null;
            if (category.length() > 32) {
                System.out.println("Entry too long for category");
            }
        } while (category.length() > 32);
        return category;
    }

    @Override
    public Date getDateFilter() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        boolean isParseOk = false;
        String input;
        Date result = new Date();
        do {
            input = getData("Enter the date (dd.MM.yyyy) or press Enter to skip this filter");
            try {
                if (input.isBlank()) {return null;}
                result = formatter.parse(input);
                isParseOk = true;
            } catch (ParseException e) {
                System.out.println("Invalid date");
            }
        } while (!isParseOk);
        return result;
    }

    private String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}