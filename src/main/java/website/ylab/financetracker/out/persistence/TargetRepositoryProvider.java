package website.ylab.financetracker.out.persistence;

import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProviderImplementation;
import website.ylab.financetracker.out.persistence.postgre.target.PostgresTargetRepository;
import website.ylab.financetracker.out.persistence.ram.target.RamTargetRepo;

/**
 * Provides an implementation of a target repository
 */
public class TargetRepositoryProvider {
    private static final ConnectionProvider connectionProvider = new ConnectionProviderImplementation();
    private static final TargetRepository repository = createRepository();

    /**
     * Provides an implementation of a target repository
     * @return repository singleton
     */
    public static TargetRepository getRepository() {
        return repository;
    }

    private static TargetRepository createRepository() {
        if (connectionProvider.getPersistenceType().equalsIgnoreCase("postgresql")) {
            return new PostgresTargetRepository(connectionProvider);
        }
        if (connectionProvider.getPersistenceType().equalsIgnoreCase("ram")) {
            return new RamTargetRepo();
        }
        throw new IllegalArgumentException("Unsupported persistence type: " + connectionProvider.getPersistenceType());
    }
}