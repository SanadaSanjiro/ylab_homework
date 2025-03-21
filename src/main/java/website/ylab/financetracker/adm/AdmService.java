package website.ylab.financetracker.adm;

import website.ylab.financetracker.ServiceProvider;
import website.ylab.financetracker.auth.Role;
import website.ylab.financetracker.auth.UserService;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.transactions.TrackerTransaction;
import website.ylab.financetracker.transactions.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Provides methods for admin's operations.
 */
public class AdmService {
    private final UserService userService = ServiceProvider.getUserService();
    private final TransactionService transactionService = ServiceProvider.getTransactionService();

    public List<UserResponse> getUsers() {
        return ServiceProvider.getUserService().getAllUsersResponse();
    }

    public List<TrackerTransaction> getUserTransactions(long userId) {
        List<TrackerTransaction> list = new ArrayList<>();
        UserResponse response = getUserById(userId);
        if (Objects.isNull(response)) return list;
        return transactionService.getUserTransaction(userId);
    }

    public UserResponse blockUser(long userId) {
        return userService.blockUser(userId);
    }

    public UserResponse unblockUser(long userId) {
        return userService.unblockUser(userId);
    }

    public UserResponse deleteUser(long userId) {
        return userService.deleteUser(userId);
    }

    public UserResponse changeUserRole(Role role, long userId) {
        UserResponse response = getUserById(userId);
        if (Objects.isNull(response)) return null;
        return userService.changeUserRole(userId, role);
    }

    private UserResponse getUserById(long id) {
        return userService.getById(id);
    }
}