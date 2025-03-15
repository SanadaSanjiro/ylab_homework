package website.ylab.financetracker;

import website.ylab.financetracker.adm.AdmService;
import website.ylab.financetracker.auth.UserAuthService;
import website.ylab.financetracker.auth.UserRegistrationService;
import website.ylab.financetracker.out.persistence.UserRepositoryProvider;
import website.ylab.financetracker.auth.UserService;
import website.ylab.financetracker.out.persistence.BudgetRepositoryProvider;
import website.ylab.financetracker.budget.BudgetService;
import website.ylab.financetracker.stat.StatService;
import website.ylab.financetracker.out.persistence.TargetRepositoryProvider;
import website.ylab.financetracker.targets.TargetService;
import website.ylab.financetracker.out.persistence.TransactionRepositoryProvider;
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
    private static final TargetService targetService = new TargetService(TargetRepositoryProvider.getRepository());
    private static final StatService statService = new StatService();
    private static final AdmService admService = new AdmService();

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
    public static TargetService getTargetService() {
        return targetService;
    }
    public static StatService getStatService() {
        return statService;
    };
    public static AdmService getAdmService() {
        return admService;
    }
}
