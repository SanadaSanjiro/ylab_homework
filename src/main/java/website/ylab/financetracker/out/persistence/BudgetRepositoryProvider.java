package website.ylab.financetracker.out.persistence;

import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProviderImplementation;
import website.ylab.financetracker.out.persistence.postgre.budget.PostgreBudgetRepository;
import website.ylab.financetracker.out.persistence.ram.budget.RamBudgetRepo;

/**
 * Provides an implementation of a budget repository
 */
public class BudgetRepositoryProvider {
    private static final ConnectionProvider connectionProvider = new ConnectionProviderImplementation();
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
        if (connectionProvider.getPersistenceType().equalsIgnoreCase("ram")) {
            return new RamBudgetRepo();
        }
        throw new IllegalArgumentException("Unsupported persistence type: " + connectionProvider.getPersistenceType());
    }
}