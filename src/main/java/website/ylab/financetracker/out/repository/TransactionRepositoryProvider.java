package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.util.ConnectionProvider;
import website.ylab.financetracker.util.ConnectionProviderImplementation;
import website.ylab.financetracker.out.repository.postgre.transacion.PostgreTransactionRepository;

/**
 * Provides an implementation of a transaction repository
 */

public class TransactionRepositoryProvider {
    private static final ConnectionProvider connectionProvider = ServiceProvider.getConnectionProvider();
    private final static TrackerTransactionRepository repository = createRepository();

    /**
     * Provides an implementation of a transaction repository
     * @return repository singleton
     */
    public static TrackerTransactionRepository getRepository() {
        return repository;
    }

    private static TrackerTransactionRepository createRepository() {
        if (connectionProvider.getPersistenceType().equalsIgnoreCase("postgresql")) {
            return new PostgreTransactionRepository(connectionProvider);
        }
        throw new IllegalArgumentException("Unsupported persistence type: " + connectionProvider.getPersistenceType());
    }
}
