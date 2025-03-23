package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.util.ConnectionProvider;
import website.ylab.financetracker.util.ConnectionProviderImplementation;
import website.ylab.financetracker.out.repository.postgre.auth.PostgreUserRepository;

/**
 * Provides an implementation of a user repository
 */
public class UserRepositoryProvider {
    private static final ConnectionProvider connectionProvider = ServiceProvider.getConnectionProvider();
    private static final TrackerUserRepository repository = createRepository();

    /**
     * Provides an implementation of a user repository
     * @return repository singleton
     */
    public static TrackerUserRepository getRepository() {
        return repository;
    }

    private static TrackerUserRepository createRepository() {
        if (connectionProvider.getPersistenceType().equalsIgnoreCase("postgresql")) {
            return new PostgreUserRepository(connectionProvider);
        }
        throw new IllegalArgumentException("Unsupported persistence type: " + connectionProvider.getPersistenceType());
    }
}