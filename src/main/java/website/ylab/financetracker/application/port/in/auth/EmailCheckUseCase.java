package website.ylab.financetracker.application.port.in.auth;

/**
 * Provides if provided email not registered in the system
 */
public interface EmailCheckUseCase {
    /**
     * Checks if provided user's email not registered in the system
     * @param email String username to check
     * @return true, if user with such name not registered in the system
     */
    boolean isUniqueEmail(String email);
}
