package website.ylab.financetracker.service.stat;

import website.ylab.financetracker.in.dto.stat.BalanceResponse;
import website.ylab.financetracker.in.dto.stat.CategoryExpensesResponse;
import website.ylab.financetracker.in.dto.stat.ReportResponse;
import website.ylab.financetracker.in.dto.stat.TurnoverResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.util.*;
import java.util.stream.Collectors;

public class StatService {
    private final TransactionService transactionService = ServiceProvider.getTransactionService();

    /**
     * Gets overall balance by user ID
     * @param userId user's ID
     * @return BalanceResponse with income, outcome and balance = income-outcome fields
     */
    public BalanceResponse getBalance(long userId) {
        List<TransactionResponse> transactions = transactionService.getUserTransaction(userId);
        double income = transactions.stream()
                .filter(t -> t.getType().equals(TransactionType.INCOME.toString()))
                .mapToDouble(TransactionResponse::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        double outcome = transactions.stream()
                .filter(t -> t.getType().equals(TransactionType.EXPENSE.toString()))
                .mapToDouble(TransactionResponse::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        return new BalanceResponse().setIncome(income).setOutcome(outcome).setBalance(income-outcome);
    }

    /**
     * Get turnover from selected date
     * @param request TurnoverRequestwith user's id and start date to count turnover
     * @return TurnoverResponse with double income and outcome fields
     */
    public TurnoverResponse getTurnover(TurnoverRequest request) {
        List<TransactionResponse> transactions = transactionService.getUserTransaction(request.getUserid());
        double income = transactions.stream()
                .filter(t->t.getDate().after(request.getStartDate()))
                .filter(t -> t.getType().equals(TransactionType.INCOME.toString()))
                .mapToDouble(TransactionResponse::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        double outcome = transactions.stream()
                .filter(t->t.getDate().after(request.getStartDate()))
                .filter(t -> t.getType().equals(TransactionType.EXPENSE.toString()))
                .mapToDouble(TransactionResponse::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
        return new TurnoverResponse().setIncome(income).setOutcome(outcome);
    }

    /**
     * Get expenses grouped by categories
     * @param userId user's ID
     * @return CategoryExpensesResponse containing a Map<String, Double> where String is the name of the category
     * and Double is the total expenses in that category.
     */
    public CategoryExpensesResponse expensesByCategory(long userId) {
        List<TransactionResponse> transactions = transactionService.getUserTransaction(userId);
        Map<String, Double> expenses = transactions.stream()
                .filter(t->t.getType().equals(TransactionType.EXPENSE.toString()))
                .collect(Collectors.groupingBy(TransactionResponse::getCategory,
                        Collectors.summingDouble(TransactionResponse::getAmount)));
        return new CategoryExpensesResponse().setExpenses(expenses);
    }

    /**
     * Get overall report with all three previous objects. Turnover counts for last month
     * @param userId user's ID
     * @return ReportResponse containing CategoryExpensesResponse, TurnoverResponse formed for last month,
     * and BalanceResponse
     */
    public ReportResponse getReport(long userId) {
        return new ReportResponse()
                .setBalance(getBalance(userId))
                .setTurnover(getTurnover(getTurnoverRequest(userId)))
                .setCategory(expensesByCategory(userId));
    }

    private TurnoverRequest getTurnoverRequest(long userId) {
        TurnoverRequest turnoverRequest = new TurnoverRequest();
        turnoverRequest.setUserid(userId);
        turnoverRequest.setStartDate(getStartDate());
        return turnoverRequest;
    }

    private Date getStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1000);
        return calendar.getTime();
    }
}