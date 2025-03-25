package website.ylab.financetracker.service.api;

import website.ylab.financetracker.in.dto.api.EmailNotification;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.service.budget.BudgetService;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.util.*;

/**
 * Provides methods for API.
 */
public class ApiService {
    private final BudgetService budgetService;
    private final TransactionService transactionService;
    private final UserService userService;

    public ApiService(BudgetService budgetService, TransactionService transactionService, UserService userService) {
        this.budgetService = budgetService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    public boolean isExceeded(long userId) {
        BudgetResponse response = budgetService.getByUserId(userId);
        if (Objects.isNull(response)) { return  false; }
        double limit = response.getLimit();
        double expenses = getExpenses(userId);
        return limit < expenses;
    }

    public List<EmailNotification> getEmailNotifications() {
        List<TrackerUser> users = userService.getAllUsers();
        List<EmailNotification> notifications = new ArrayList<>();
        for (TrackerUser user: users) {
            BudgetResponse response = budgetService.getByUserId(user.getId());
            if (Objects.nonNull(response)&&isExceeded(user.getId())) {
                EmailNotification notification = createNotification(user);
                notifications.add(notification);
            }
        }
        return notifications;
    }

    private EmailNotification createNotification(TrackerUser user) {
        String text = "Dear " + user.getUsername() + ". We notify you when your budget is exceeded." +
                "With best regards, your TrackingService";
        return new EmailNotification(user.getEmail(), text);
    }

    private double getExpenses(long userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();

        List<TransactionResponse> transactions = transactionService.getUserTransaction(userId);
        return transactions.stream()
                .filter(t->t.getDate().after(startDate))
                .filter(t -> t.getType().equals(TransactionType.EXPENSE.toString()))
                .mapToDouble(TransactionResponse::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
    }
}