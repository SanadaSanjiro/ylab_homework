package website.ylab.financetracker.application.domain.service.stat;

import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;
import website.ylab.financetracker.application.domain.service.transaction.TransactionService;
import website.ylab.financetracker.application.port.in.stat.StatUseCase;
import website.ylab.financetracker.transactions.TransactionType;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatService implements StatUseCase {
    private final TransactionService transactionService;

    public StatService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public String getBalance() {
        List<TransactionModel> transactions = transactionService
                .show(null, null, TransactionType.INCOME);
        double income = transactions.stream()
                .mapToDouble(TransactionModel::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        double expenses = transactionService
                .show(null, null, TransactionType.EXPENSE)
                .stream()
                .mapToDouble(TransactionModel::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        return "Your balance: " + (income-expenses) + ". Your income: " + income
                + ". Your expenses: " + expenses;
    }

    @Override
    public String getTurnover(Date startDate) {
        List<TransactionModel> transactions = transactionService
                .show(null, null, TransactionType.INCOME);
        double income = transactions.stream()
                .filter(t->t.getDate().after(startDate))
                .mapToDouble(TransactionModel::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        double expenses = transactionService
                .show(null, null, TransactionType.EXPENSE)
                .stream()
                .filter(t->t.getDate().after(startDate))
                .mapToDouble(TransactionModel::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        return "Your income for the period:" + income + ". Your expenses for the period: " + expenses;
    }

    @Override
    public String expensesByCategory() {
        List<TransactionModel> transactions = transactionService
                .show(null,null, TransactionType.EXPENSE);
        Map<String, Double> expenses = transactions.stream()
                .filter(t->t.getType().equals(TransactionType.EXPENSE))
                .collect(Collectors.groupingBy(TransactionModel::getCategory,
                        Collectors.summingDouble(TransactionModel::getAmount)));
        return expenses.toString();
    }

    @Override
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