package website.ylab.financetracker.adapter.in.auth;

import java.util.regex.Pattern;

/**
 * Implementation of UserVerifierInterface. Provides methods for checking the validity and uniqueness of user input data
 */

public class UserDataVerifier implements UserVerifierInterface {
    private final Pattern emailPattern;
    private final Pattern namePattern;

    public UserDataVerifier(String emailTemplate, String nameRegexp) {
        this.emailPattern = Pattern.compile(emailTemplate);
        this.namePattern = Pattern.compile(nameRegexp);
    }

    @Override
    public boolean isValidName(String name) {
        return namePattern.matcher(name).matches();
    }

    /**
     * Checks email validity
     * @param email email address for validation
     * @return true if email is valid
     */
    @Override
    public boolean isValidEmail(String email) {
        return emailPattern.matcher(email).matches();
    }
}