package website.ylab.financetracker.application.domain.service.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.domain.service.budget.BudgetService;
import website.ylab.financetracker.application.domain.service.target.TargetService;
import website.ylab.financetracker.application.domain.service.transaction.TransactionService;

public class UserDataRemovingService {
    private final TransactionService transactionService;
    private final BudgetService budgetService;
    public final TargetService targetService;

    public UserDataRemovingService(TransactionService transactionService, BudgetService budgetService, TargetService targetService) {
        this.transactionService = transactionService;
        this.budgetService = budgetService;
        this.targetService = targetService;
    }

    public void removeUserData(UserModel user) {
        long userId = user.getId();
        targetService.deleteTargetById(userId);
        budgetService.deleteById(userId);
        transactionService.deleteByUser(userId);
    }
}
