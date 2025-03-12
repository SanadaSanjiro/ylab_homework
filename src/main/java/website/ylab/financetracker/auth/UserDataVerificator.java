package website.ylab.financetracker.auth;

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
    /**
     * Static method to check username uniqueness
     * @param trackerUserRepository User Repository
     * @param username User name
     * @return true if the username is not already taken
     */
    public static boolean isUniqueName(TrackerUserRepository trackerUserRepository, String username) {
        return trackerUserRepository.getAllUsers().stream().noneMatch(trackerUser
                -> trackerUser.getUsername().equals(username.toLowerCase()));
    }

    /**
     * Static method to check email uniqueness
     * @param trackerUserRepository User Repository
     * @param email email address for verification
     * @return true if the email is not already taken
     */
    public static boolean isUniqueEmail(TrackerUserRepository trackerUserRepository, String email) {
        return trackerUserRepository.getAllUsers().stream().noneMatch(trackerUser
                -> trackerUser.getEmail().equals(email.toLowerCase()));
    }

    /**
     * Static method to check name validity
     * @param name Must be between 1 and 20 characters long and contain only Latin letters and numbers.
     * @return true if name is valid
     */
    public static boolean isValidName(String name) {
        return namePattern.matcher(name).matches();
    }

    /**
     * Static method to check email validity
     * @param email email address for validation
     * @return true if email is valid
     */
    public static boolean isValidEmail(String email) {
        return emailPattern.matcher(email).matches();
    }
}