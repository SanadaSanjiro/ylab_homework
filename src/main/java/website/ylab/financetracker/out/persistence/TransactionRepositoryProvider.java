package website.ylab.financetracker.out.persistence;

import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProviderImplementation;
import website.ylab.financetracker.out.persistence.postgre.transacion.PostgreTransactionRepository;
import website.ylab.financetracker.out.persistence.ram.transaction.RamTransactionRepo;

/**
 * Provides an implementation of a transaction repository
 */

public class TransactionRepositoryProvider {
    private static final ConnectionProvider connectionProvider = new ConnectionProviderImplementation();
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
        if (connectionProvider.getPersistenceType().equalsIgnoreCase("ram")) {
            return new RamTransactionRepo();
        }
        throw new IllegalArgumentException("Unsupported persistence type: " + connectionProvider.getPersistenceType());
    }
}
