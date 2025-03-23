package website.ylab.financetracker.service.stat;

import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.util.*;
import java.util.stream.Collectors;

public class StatService {
    private final TransactionService transactionService = ServiceProvider.getTransactionService();

    public String getBalance(long userId) {
        List<TransactionResponse> transactions = transactionService.getUserTransaction(userId);
        double income = transactions.stream()
                .filter(t -> t.getType().equals(TransactionType.INCOME.toString()))
                .mapToDouble(TransactionResponse::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        double expenses = transactions.stream()
                .filter(t -> t.getType().equals(TransactionType.EXPENSE.toString()))
                .mapToDouble(TransactionResponse::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        return "Your balance: " + (income-expenses) + ". Your income: " + income
                + ". Your expenses: " + expenses;
    }

    public String getTurnover(long userId, Date startDate) {
        List<TransactionResponse> transactions = transactionService.getUserTransaction(userId);
        double income = transactions.stream()
                .filter(t->t.getDate().after(startDate))
                .filter(t -> t.getType().equals(TransactionType.INCOME.toString()))
                .mapToDouble(TransactionResponse::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        double expenses = transactions.stream()
                .filter(t->t.getDate().after(startDate))
                .filter(t -> t.getType().equals(TransactionType.EXPENSE.toString()))
                .mapToDouble(TransactionResponse::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        return "Your income for the period:" + income + ". Your expenses for the period by categories: " + expenses;
    }

    public String expensesByCategory(long userId) {
        List<TransactionResponse> transactions = transactionService.getUserTransaction(userId);
        Map<String, Double> expenses = transactions.stream()
                .filter(t->t.getType().equals(TransactionType.EXPENSE.toString()))
                .collect(Collectors.groupingBy(TransactionResponse::getCategory,
                        Collectors.summingDouble(TransactionResponse::getAmount)));
        return expenses.toString();
    }

    public String getReport(long userId) {
        return getBalance(userId) + " " +
                getTurnover(userId, setStartDate()) + " " +
                expensesByCategory(userId);
    }

    private Date setStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1000);
        return calendar.getTime();
    }
}