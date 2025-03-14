package website.ylab.financetracker.adapter.util;

/**
 * Provides constant data for different utils
 */
public class ConstantStorage {
    /**
     * Email Verification Template
     */
    public static final String MAIL_REGEXP = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)" +
            "*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    /**
     * Username Check Template
     */
    public static final String NAME_REGEXP = "^[A-Za-z0-9]\\w{1,20}$";
}
