package website.ylab.financetracker.service.auth;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Provides methods for checking the validity and uniqueness of user input data
 */

@Service
public class UserDataVerificator {
    private final String MAIL_REGEXP = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)" +
            "*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final Pattern emailPattern = Pattern.compile(MAIL_REGEXP);
    private final String NAME_REGEXP = "^[A-Za-z0-9]\\w{1,20}$";
    private final Pattern namePattern = Pattern.compile(NAME_REGEXP);
    private final String PASSWORD_REGEXP = "^[A-Za-z0-9]\\w{1,20}$";
    private final Pattern passPattern = Pattern.compile(PASSWORD_REGEXP);

    /**
     * Static method to check name validity
     * @param name Must be between 1 and 20 characters long and contain only Latin letters and numbers.
     * @return true if name is valid
     */
    public boolean isValidName(String name) {
        return Objects.nonNull(name) && namePattern.matcher(name).matches();
    }

    /**
     * Static method to check email validity
     * @param email email address for validation
     * @return true if email is valid
     */
    public boolean isValidEmail(String email) {
        return Objects.nonNull(email) && emailPattern.matcher(email).matches();
    }

    /**
     * Static method to check password validity
     * @param password password for validation
     * @return true if password is valid
     */
    public boolean isValidPassword(String password) {
        return Objects.nonNull(password) && passPattern.matcher(password).matches();
    }
}