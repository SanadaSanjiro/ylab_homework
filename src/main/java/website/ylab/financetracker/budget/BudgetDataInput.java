package website.ylab.financetracker.budget;

import website.ylab.financetracker.ServiceProvider;
import website.ylab.financetracker.api.ApiService;
import website.ylab.financetracker.api.EmailNotification;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.auth.UserAuthService;
import website.ylab.financetracker.transactions.TrackerTransaction;
import website.ylab.financetracker.transactions.TransactionType;

import java.util.*;

/**
 * Prompts the user for information required for budget operations
 */
public class BudgetDataInput {
    private final Scanner scanner = new Scanner(System.in);
    private final BudgetService budgetService = ServiceProvider.getBudgetService();
    private final ApiService apiService;

    public BudgetDataInput() {
        apiService=new ApiService(this);
    }

    public boolean isExceedBudget() {
        TrackerUser user = UserAuthService.getCurrentUser();
        return apiService.isExceeded(user);
    }

    public List<EmailNotification> getEmailNotifications() {
        return apiService.getEmailNotifications();
    }

    public String setBudget() {
        TrackerUser user = UserAuthService.getCurrentUser();
        if (Objects.isNull(user)) return "You should log in first";
        double limit=0;
        do {
            try {
                limit = Double.parseDouble(getData(
                        "Enter a new budget constraint. It must be greater than zero."));
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Invalid amount");
            }
        } while (limit <= 0);
        return budgetService.setBudget(user, limit);
    }

    public String getBudget() {
        TrackerUser user = UserAuthService.getCurrentUser();
        if (Objects.isNull(user)) return "You should log in first";
        Double limit = budgetService.getBudget(user);
        if (Objects.isNull(limit)) { return "No budget limits found"; }
        double expenses = getExpenses(user);
        boolean budgetIncrease = limit > expenses;
        if (budgetIncrease) {
            return "Current budget = " + limit + " was not exceeded. Remainder = " + (limit - expenses);
        }
        return "Budget exceeded! Limit: " + limit + ". Current expenses: " + expenses
                + ". Exceeded: " + (limit - expenses);
    }

    public boolean isExceeded(TrackerUser user) {
        double limit = budgetService.getBudget(user);
        double expenses = getExpenses(user);
        return limit < expenses;
    }

    private double getExpenses(TrackerUser user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();

        List<TrackerTransaction> transactions = ServiceProvider.getTransactionService().getAllTransactions();
        return transactions.stream()
                .filter(t->t.getDate().after(startDate))
                .filter(t -> t.getType().equals(TransactionType.EXPENSE))
                .mapToDouble(TrackerTransaction::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
    }

    private String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
