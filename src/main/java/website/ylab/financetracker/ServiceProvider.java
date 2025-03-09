package website.ylab.financetracker;

import website.ylab.financetracker.auth.UserAuthService;
import website.ylab.financetracker.auth.UserRegistrationService;
import website.ylab.financetracker.auth.UserRepositoryProvider;
import website.ylab.financetracker.auth.UserService;
import website.ylab.financetracker.budget.BudgetRepositoryProvider;
import website.ylab.financetracker.budget.BudgetService;
import website.ylab.financetracker.transactions.TransactionRepositoryProvider;
import website.ylab.financetracker.transactions.TransactionService;

/**
 * Provides services
 */
public class ServiceProvider {
    private static final UserAuthService userAuthService = new UserAuthService(UserRepositoryProvider.getRepository());
    private static final UserRegistrationService userRegistrationService = new UserRegistrationService(
            UserRepositoryProvider.getRepository());
    private static final UserService userService = new UserService(UserRepositoryProvider.getRepository());
    private static final TransactionService transactionService = new TransactionService(
            TransactionRepositoryProvider.getRepository());
    private static final BudgetService budgetService = new BudgetService(BudgetRepositoryProvider.getRepository());

    public static UserAuthService getUserAuthService() {
        return userAuthService;
    }
    public static UserRegistrationService getUserRegistrationService() {
        return userRegistrationService;
    }
    public static UserService getUserService() {
        return userService;
    }
    public static TransactionService getTransactionService() {
        return transactionService;
    }
    public static BudgetService getBudgetService() {
        return budgetService;
    }
}
