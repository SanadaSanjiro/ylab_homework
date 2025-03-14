package website.ylab.financetracker.application.port.in.auth;

/**
 * Provides if provided user's name not registered in the system
 */
public interface NameCheckUseCase {
    /**
     * Checks if provided user's name not registered in the system
     * @param username String username to check
     * @return true, if user with such name not registered in the system
     */
    boolean isUniqueName(String username);
}