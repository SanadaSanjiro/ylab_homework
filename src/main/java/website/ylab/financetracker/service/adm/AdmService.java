package website.ylab.financetracker.service.adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.transactions.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Provides methods for admin's operations.
 */
@Service
public class AdmService {
    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public AdmService(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    public List<UserResponse> getUsers() {
        return userService.getAllUsersResponse();
    }

    public List<TransactionResponse> getUserTransactions(long userId) {
        List<TransactionResponse> list = new ArrayList<>();
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
        return userService.getResponseById(id);
    }
}