package website.ylab.financetracker.in.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Email template
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailNotification {
    String email;
    String body;

    @Override
    public String toString() {
        return "\nEmail\n" +
                "mailto='" + email + '\n' +
                body +
                "\n===================================";
    }
}