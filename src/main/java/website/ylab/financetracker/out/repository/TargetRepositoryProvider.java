package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.util.ConnectionProvider;
import website.ylab.financetracker.util.ConnectionProviderImplementation;
import website.ylab.financetracker.out.repository.postgre.target.PostgresTargetRepository;

/**
 * Provides an implementation of a target repository
 */
public class TargetRepositoryProvider {
    private static final ConnectionProvider connectionProvider = ServiceProvider.getConnectionProvider();
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
        throw new IllegalArgumentException("Unsupported persistence type: " + connectionProvider.getPersistenceType());
    }
}