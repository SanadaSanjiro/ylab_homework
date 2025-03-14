package website.ylab.financetracker.adapter.in.api;

import website.ylab.financetracker.application.domain.model.api.EmailNotification;
import website.ylab.financetracker.application.port.in.api.ApiUseCase;

import java.util.List;

public class ApiAdapter {
    private final ApiUseCase api;

    public ApiAdapter(ApiUseCase api) {
        this.api = api;
    }

    public boolean isExceeded() {
        return api.isExceeded();
    }

    public List<EmailNotification> getEmailNotifications() {
        return api.getEmailNotifications();
    }
}
