package website.ylab.financetracker.api;

import website.ylab.financetracker.ServiceProvider;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.budget.BudgetDataInput;
import website.ylab.financetracker.budget.BudgetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Provides methods for API.
 */
public class ApiService {
    private final BudgetDataInput budgetDataInput;

    public ApiService(BudgetDataInput budgetDataInput) {
        this.budgetDataInput = budgetDataInput;
    }

    public boolean isExceeded(TrackerUser user) {
        return budgetDataInput.isExceeded(user);
    }

    public List<EmailNotification> getEmailNotifications() {
        List<TrackerUser> users = ServiceProvider.getUserService().getAllUsers();
        BudgetService budgetService = ServiceProvider.getBudgetService();
        List<EmailNotification> notifications = new ArrayList<>();
        for (TrackerUser user: users) {
            Double budget = budgetService.getBudget(user);
            if (Objects.nonNull(budget)&&isExceeded(user)) {
                EmailNotification notification = createNotification(user);
                notifications.add(notification);
            }
        }
        return notifications;
    }

    private EmailNotification createNotification(TrackerUser user) {
        String text = "Dear " + user.getUsername() + "\nWe notify you when your budget is exceeded.\n" +
                "With best regards, your TrackingService";
        return new EmailNotification(user.getEmail(), text);
    }
}
