package website.ylab.financetracker.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.ylab.financetracker.service.budget.BudgetService;
import website.ylab.financetracker.service.targets.TargetService;
import website.ylab.financetracker.service.transactions.TransactionService;

@Service
public class UserDataDeletionService {
    TransactionService transactionService;
    BudgetService budgetService;
    TargetService targetService;

    @Autowired
    public UserDataDeletionService(TransactionService transactionService,
                                   BudgetService budgetService,
                                   TargetService targetService) {
        this.transactionService = transactionService;
        this.budgetService = budgetService;
        this.targetService = targetService;
    }

    public void deleteUserData(long userid) {
        transactionService.deleteUserTransactions(userid);
        budgetService.deleteByUserId(userid);
        targetService.deleteByUserId(userid);
    }
}