package website.ylab.financetracker.adm;

import website.ylab.financetracker.ServiceProvider;
import website.ylab.financetracker.auth.Role;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.transactions.TrackerTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provides methods for admin's operations.
 */
public class AdmService {
    public List<TrackerUser> getUsers() {
        return ServiceProvider.getUserService().getAllUsers();
    }

    public List<TrackerTransaction> getUserTransactions(long userId) {
        List<TrackerTransaction> list = new ArrayList<>();
        Optional<TrackerUser> optional = getUserById(userId);
        if (optional.isEmpty()) return list;
        return ServiceProvider.getTransactionService().getUserTransaction(optional.get());
    }

    public String blockUser(long userId) {
        Optional<TrackerUser> optional = getUserById(userId);
        if (optional.isEmpty()) return "User not found";
        optional.get().setEnabled(false);
        return "User blocked";
    }

    public String deleteUser(long userId) {
        Optional<TrackerUser> optional = getUserById(userId);
        if (optional.isEmpty()) return "User not found";
        TrackerUser user = optional.get();
        return ServiceProvider.getUserService().deleteUser(user);
    }

    public String changeUserRole(Role role, Long userId) {
        Optional<TrackerUser> optional = getUserById(userId);
        if (optional.isEmpty()) return "User not found";
        TrackerUser user = optional.get();
        user.setRole(role);
        return "User role changed";
    }

    private Optional<TrackerUser> getUserById(long id) {
        return ServiceProvider.getUserService().getAllUsers().stream()
                .filter(u->u.getId()==id)
                .findFirst();
    }
}
