package website.ylab.financetracker.stat;

import website.ylab.financetracker.ServiceProvider;
import website.ylab.financetracker.transactions.TrackerTransaction;
import website.ylab.financetracker.transactions.TransactionService;
import website.ylab.financetracker.transactions.TransactionType;

import java.util.*;
import java.util.stream.Collectors;

public class StatService {
    private final TransactionService transactionService = ServiceProvider.getTransactionService();

    public String getBalance() {
        List<TrackerTransaction> transactions = transactionService.getAllTransactions();
        double income = transactions.stream()
                .filter(t -> t.getType().equals(TransactionType.INCOME))
                .mapToDouble(TrackerTransaction::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        double expenses = transactions.stream()
                .filter(t -> t.getType().equals(TransactionType.EXPENSE))
                .mapToDouble(TrackerTransaction::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        return "Your balance: " + (income-expenses) + ". Your income: " + income
                + ". Your expenses: " + expenses;
    }

    public String getTurnover(Date startDate) {
        List<TrackerTransaction> transactions = transactionService.getAllTransactions();
        double income = transactions.stream()
                .filter(t->t.getDate().after(startDate))
                .filter(t -> t.getType().equals(TransactionType.INCOME))
                .mapToDouble(TrackerTransaction::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        double expenses = transactions.stream()
                .filter(t->t.getDate().after(startDate))
                .filter(t -> t.getType().equals(TransactionType.EXPENSE))
                .mapToDouble(TrackerTransaction::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        return "Your income for the period:" + income + ". Your expenses for the period by categories: " + expenses;
    }

    public String expensesByCategory() {
        List<TrackerTransaction> transactions = transactionService.getAllTransactions();
        Map<String, Double> expenses = transactions.stream()
                .filter(t->t.getType().equals(TransactionType.EXPENSE))
                .collect(Collectors.groupingBy(TrackerTransaction::getCategory,
                        Collectors.summingDouble(TrackerTransaction::getAmount)));
        return expenses.toString();
    }

    public String getReport() {
        return getBalance() + " " +
                getTurnover(setStartDate()) + " " +
                expensesByCategory();
    }

    private Date setStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1000);
        return calendar.getTime();
    }
}