package website.ylab.financetracker.adapter.in.auth;

/**
 * Provides methods for checking the validity of user input data
 */
public interface UserVerifierInterface {
    /**
     * Checks name validity
     * @param name Must be between 1 and 20 characters long and contain only Latin letters and numbers.
     * @return true if name is valid
     */
    boolean isValidName(String name);
    /**
     * Checks email validity
     * @param email email address for validation
     * @return true if email is valid
     */
    boolean isValidEmail(String email);
}