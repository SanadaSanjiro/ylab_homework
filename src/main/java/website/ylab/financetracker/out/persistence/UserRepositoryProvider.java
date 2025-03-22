package website.ylab.financetracker.out.persistence;

import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProviderImplementation;
import website.ylab.financetracker.out.persistence.postgre.auth.PostgreUserRepository;
import website.ylab.financetracker.out.persistence.ram.auth.RamUserRepo;

/**
 * Provides an implementation of a user repository
 */
public class UserRepositoryProvider {
    private static final ConnectionProvider connectionProvider = new ConnectionProviderImplementation();
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
        if (connectionProvider.getPersistenceType().equalsIgnoreCase("ram")) {
            return new RamUserRepo();
        }
        throw new IllegalArgumentException("Unsupported persistence type: " + connectionProvider.getPersistenceType());
    }
}