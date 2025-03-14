package website.ylab.financetracker.adapter.in.auth;

/**
 * Pack of methods to get users data
 */
public interface UserInterface {
    /**
     * Should return verified username in String format for user data adapters.
     * @return valid email in String format. It must be between 1 and 20 characters long
     * and contain only Latin letters and numbers
     */
    String getName();

    /**
     * Should return verified email in String format for user data adapters.
     * @return valid email in String format.
     */
    String getEmail();

    /**
     * Should return verified password in String format for user data adapters.
     * @return password in String format, must be between 1 and 20 characters.
     */
    String getPassword();

    /**
     * Used to get a different users inputs. Should display incoming message and return users input
     * @param message Message in String format, that should be displayed for a user
     * @return user's input in String format
     */
    String getData(String message);
}