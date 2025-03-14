package website.ylab.financetracker.application.domain.service.adm;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;
import website.ylab.financetracker.application.domain.service.auth.SessionProvider;
import website.ylab.financetracker.application.domain.service.auth.UserDataService;
import website.ylab.financetracker.application.domain.model.auth.Role;
import website.ylab.financetracker.application.domain.service.transaction.TransactionService;
import website.ylab.financetracker.application.port.in.adm.AdmUseCase;


import java.util.List;
import java.util.Optional;

public class AdmService implements AdmUseCase {
    private final UserDataService userDataService;
    private final SessionProvider sessionProvider;
    private final TransactionService transactionService;

    public AdmService(UserDataService userDataService, SessionProvider sessionProvider, TransactionService transactionService) {
        this.userDataService = userDataService;
        this.sessionProvider = sessionProvider;
        this.transactionService = transactionService;
    }

    @Override
    public List<UserModel> getUsers() {
        if (isNotAdmin()) return null;
        return userDataService.getAllUsers();
    }

    @Override
    public List<TransactionModel> getUserTransactions(long userId) {
        if (isNotAdmin()) return null;
        return transactionService.getByUserId(userId);
    }

    @Override
    public UserModel blockUser(long userId) {
        if (isNotAdmin()) return null;
        return userDataService.blockUser(userId).orElse(null);
    }

    @Override
    public UserModel unblockUser(long userId) {
        if (isNotAdmin()) return null;
        return userDataService.unblockUser(userId).orElse(null);
    }

    @Override
    public UserModel deleteUser(long userId) {
        if (isNotAdmin()) return null;
        return userDataService.deleteUser(userId);
    }

    @Override
    public UserModel changeUserRole(Role role, long userId) {
        if (isNotAdmin()) return null;
        Optional<UserModel> optional = userDataService.getUser(userId);
        if (optional.isEmpty()) return null;
        UserModel user = optional.get();
        user.setRole(role);
        return userDataService.updateUser(user).orElse(null);
    }

    @Override
    public boolean isAdmin() {
        return !isNotAdmin();
    }

    private boolean isNotAdmin() {
        UserModel currentUser = sessionProvider.getCurrentUser();
        return currentUser.getRole().equals(Role.ADMIN);
    }
}