package website.ylab.financetracker.service;

import lombok.Getter;
import website.ylab.financetracker.service.adm.AdmService;
import website.ylab.financetracker.out.repository.UserRepositoryProvider;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.out.repository.BudgetRepositoryProvider;
import website.ylab.financetracker.service.budget.BudgetService;
import website.ylab.financetracker.service.stat.StatService;
import website.ylab.financetracker.out.repository.TargetRepositoryProvider;
import website.ylab.financetracker.service.targets.TargetService;
import website.ylab.financetracker.out.repository.TransactionRepositoryProvider;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.util.*;

/**
 * Provides services
 */
public class ServiceProvider {
    @Getter
    private static final ConnectionProvider connectionProvider = new ConnectionProviderImplementation();
    @Getter
    private static final UserService userService = new UserService(UserRepositoryProvider.getRepository());
    @Getter
    private static final TransactionService transactionService = new TransactionService(
            TransactionRepositoryProvider.getRepository());
    @Getter
    private static final BudgetService budgetService = new BudgetService(BudgetRepositoryProvider.getRepository());
    @Getter
    private static final TargetService targetService = new TargetService(TargetRepositoryProvider.getRepository());
    @Getter
    private static final StatService statService = new StatService();
    @Getter
    private static final AdmService admService = new AdmService();
}