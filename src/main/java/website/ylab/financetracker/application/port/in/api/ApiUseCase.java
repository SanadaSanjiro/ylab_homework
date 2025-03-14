package website.ylab.financetracker.application.port.in.api;

import website.ylab.financetracker.application.domain.model.api.EmailNotification;

import java.util.List;

public interface ApiUseCase {
    /**
     * Method for API to check if user's budget was exceeded
     * @return boolean true if budget exceeded
     */
    boolean isExceeded();

    /**
     * Method for API to send email notification for those users who overrun they budget
     * @return List<EmailNotification> with notifications
     */
    List<EmailNotification> getEmailNotifications();
}
