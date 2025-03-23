package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.util.ConnectionProvider;
import website.ylab.financetracker.out.repository.postgre.budget.PostgreBudgetRepository;

/**
 * Provides an implementation of a budget repository
 */
public class BudgetRepositoryProvider {
    private static final ConnectionProvider connectionProvider = ServiceProvider.getConnectionProvider();
    private static final BudgetRepository repository = createRepository();

    /**
     * Provides an implementation of a budget repository
     * @return repository singleton
     */
    public static BudgetRepository getRepository() {
        return repository;
    }

    private static BudgetRepository createRepository() {
        if (connectionProvider.getPersistenceType().equalsIgnoreCase("postgresql")) {
            return new PostgreBudgetRepository(connectionProvider);
        }
        throw new IllegalArgumentException("Unsupported persistence type: " + connectionProvider.getPersistenceType());
    }
}