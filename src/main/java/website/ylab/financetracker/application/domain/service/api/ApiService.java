package website.ylab.financetracker.application.domain.service.api;


import website.ylab.financetracker.application.domain.model.api.EmailNotification;
import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.domain.service.auth.UserDataService;
import website.ylab.financetracker.application.domain.service.budget.BudgetService;
import website.ylab.financetracker.application.port.in.api.ApiUseCase;


import java.util.ArrayList;
import java.util.List;

public class ApiService implements ApiUseCase {
    private final BudgetService budgetService;
    private final UserDataService userDataService;

    public ApiService(BudgetService budgetService, UserDataService userDataService) {
        this.budgetService = budgetService;
        this.userDataService = userDataService;
    }

    @Override
    public boolean isExceeded() {
        return budgetService.isExceeded();
    }

    @Override
    public List<EmailNotification> getEmailNotifications() {
        List<UserModel> users = userDataService.getAllUsers();
        List<EmailNotification> notifications = new ArrayList<>();
        for (UserModel user: users) {
            long id = user.getId();
            if (budgetService.isExceededForUser(id)) {
                EmailNotification notification = createNotification(user);
                notifications.add(notification);
            }
        }
        return notifications;
    }

    private EmailNotification createNotification(UserModel user) {
        String text = "Dear " + user.getUsername() + "\nWe notify you when your budget is exceeded.\n" +
                "With best regards, your TrackingService";
        EmailNotification mail = new EmailNotification();
        return mail.setEmail(user.getEmail()).setSubject("Notification of budget overrun").setBody(text);
    }
}