package website.ylab.financetracker.auth;

import website.ylab.financetracker.ServiceProvider;

import java.util.List;
import java.util.Optional;

import static website.ylab.financetracker.auth.UserDataVerificator.isUniqueEmail;
import static website.ylab.financetracker.auth.UserDataVerificator.isUniqueName;

/**
 * Provides methods for changing user data.
 */
public class UserService {
    private final TrackerUserRepository trackerUserRepository;

    public UserService(TrackerUserRepository trackerUserRepository) {
        this.trackerUserRepository = trackerUserRepository;
    }
    public String changeUser(TrackerUser newUser) {
        TrackerUser oldUser = UserAuthService.getCurrentUser();

        if (!oldUser.getUsername().equals(newUser.getUsername())) {
            if (!isUniqueName(trackerUserRepository, newUser.getUsername())) {
                return "Username is already in use";
            } else oldUser.setUsername(newUser.getUsername());
        }

        if (!oldUser.getEmail().equals(newUser.getEmail())) {
            if (!isUniqueEmail(trackerUserRepository, newUser.getEmail())) {
                return "Email is already in use";
            } else oldUser.setEmail(newUser.getEmail());
        }

        if (!oldUser.getPassword().equals(newUser.getPassword())) {
            oldUser.setPassword(newUser.getPassword());
        }
        return "User data successfully changed";
    }

    public List<TrackerUser> getAllUsers() {
        return trackerUserRepository.getAllUsers();
    }

    /**
     * Removes a user from the system. Also deletes all of their data.
     * @return String with a result.
     */
    public String deleteUser() {
        TrackerUser user = UserAuthService.getCurrentUser();
        // Удаляет все транзакции пользователя
        ServiceProvider.getTransactionService().deleteUserTransactions(user);
        ServiceProvider.getBudgetService().deleteBudget(user);
        ServiceProvider.getTargetService().deleteTarget(user);
        UserAuthService.logout();
        Optional<TrackerUser> optional = trackerUserRepository.delete(user);
        if (optional.isEmpty()) {
            return "Error deleting user";
        }
        return "User successfully deleted";
    }
}