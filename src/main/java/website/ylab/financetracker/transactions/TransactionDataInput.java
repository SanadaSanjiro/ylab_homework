package website.ylab.financetracker.transactions;

import website.ylab.financetracker.ServiceProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Prompts the user for information required for transaction operations
 */
public class TransactionDataInput {
    private final TransactionService transactionService = ServiceProvider.getTransactionService();
    private final Scanner scanner = new Scanner(System.in);

    public String createNewTransaction() {
        System.out.println("Enter new transaction data");
        TransactionType type = getType();
        double amount = getAmount();
        String category = getCategory();
        Date date = getDate();
        String description = getDescription();
        return transactionService.addNewTransaction(type, amount, category, date, description);
    }

    public String updateTransaction() {
        System.out.println("Please enter new details to update the transaction.");
        long id = getId();
        double amount = getAmount();
        String category = getCategory();
        String description = getDescription();
        return transactionService.changeTransaction(id, amount, category,description);
    }

    public String deleteTransaction() {
        System.out.println("Please enter transaction id to delete.");
        long id = getId();
        return transactionService.deleteTransaction(id);
    }

    public String getTransactions() {
        System.out.println("Please enter filters to transaction list. Press ENTER to skip filter");
        Date dateFilter = getDateFilter();
        String categoryFilter = getCategoryFilter();
        TransactionType typeFilter = getTypeFilter();
        List<TrackerTransaction> result = transactionService.getAllTransactions();
        result = TransactionFilter.filter(result, dateFilter);
        result = TransactionFilter.filter(result, typeFilter);
        result = TransactionFilter.filter(result, categoryFilter);
        return result.toString();
    }

    private TransactionType getTypeFilter() {
        Optional<TransactionType> typeOptional;
        do {
            String typeString = getData("Enter the transaction type (INCOME or EXPENSE). Press ENTER to skip filter");
            if (typeString.isEmpty()) {
                return null;
            }
            typeOptional = TransactionType.fromString(typeString);
        } while (typeOptional.isEmpty());
        return typeOptional.get();
    }

    private String getCategoryFilter() {
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

    private Date getDateFilter() {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        boolean isParseOk = false;
        do {
            String input = getData("Enter the date (dd.MM.yyyy) or press Enter to skip this filter");
            try {
                if (input.isBlank()) {return null;}
                date=formatter.parse(input);
                isParseOk = true;
            } catch (ParseException e) {
                System.out.println("Invalid date");
            }
        } while (!isParseOk);
        return date;
    }

    private TransactionType getType() {
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

    private double getAmount() {
        double amount=0;
        do {
            try {
                amount = Double.parseDouble(getData(
                        "Enter the amount. It must be greater than zero."));
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Invalid amount");
            }
        } while (amount <= 0);
        return amount;
    }

    private String getCategory() {
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


    private Date getDate() {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        boolean isParseOk = false;
        do {
            String input = getData("Enter the date (dd.MM.yyyy): ");
            try {
                date=formatter.parse(input);
                isParseOk = true;
            } catch (ParseException e) {
                System.out.println("Invalid date");
            }
        } while (!isParseOk);
        return date;
    }

    private String getDescription() {
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


    private long getId() {
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

    private String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}