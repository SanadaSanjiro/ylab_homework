package website.ylab.financetracker.service.api;

/**
 * Email template
 */
public class EmailNotification {
    String email;
    String body;

    public EmailNotification() {
    }

    public EmailNotification(String email, String body) {
        this.email = email;
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "\nEmail\n" +
                "mailto='" + email + '\n' +
                body +
                "\n===================================";
    }
}
