package website.ylab.financetracker.service.auth;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Provides methods for checking the validity and uniqueness of user input data
 */

public class UserDataVerificator {
    private static final String MAIL_REGEXP = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)" +
            "*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern emailPattern = Pattern.compile(MAIL_REGEXP);
    private static final String NAME_REGEXP = "^[A-Za-z0-9]\\w{1,20}$";
    private static final Pattern namePattern = Pattern.compile(NAME_REGEXP);
    private static final String PASSWORD_REGEXP = "^[A-Za-z0-9]\\w{1,20}$";
    private static final Pattern passPattern = Pattern.compile(PASSWORD_REGEXP);

    /**
     * Static method to check name validity
     * @param name Must be between 1 and 20 characters long and contain only Latin letters and numbers.
     * @return true if name is valid
     */
    public static boolean isValidName(String name) {
        return Objects.nonNull(name) && namePattern.matcher(name).matches();
    }

    /**
     * Static method to check email validity
     * @param email email address for validation
     * @return true if email is valid
     */
    public static boolean isValidEmail(String email) {
        return Objects.nonNull(email) && emailPattern.matcher(email).matches();
    }

    /**
     * Static method to check password validity
     * @param password password for validation
     * @return true if password is valid
     */
    public static boolean isValidPassword(String password) {
        return Objects.nonNull(password) && passPattern.matcher(password).matches();
    }
}