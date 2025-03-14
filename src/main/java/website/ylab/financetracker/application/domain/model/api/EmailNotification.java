package website.ylab.financetracker.application.domain.model.api;

/**
 * Email template
 */
public class EmailNotification {
    String email;
    String subject;
    String body;

    public String getEmail() {
        return email;
    }

    public EmailNotification setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public EmailNotification setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public EmailNotification setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return "EmailNotification{" +
                "email='" + email + '\n' +
                ", body='" + body +
                '}';
    }
}