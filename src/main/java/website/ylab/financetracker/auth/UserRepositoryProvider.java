package website.ylab.financetracker.auth;

/**
 * Provides an implementation of a user repository
 */
public class UserRepositoryProvider {
    private static final TrackerUserRepository userRepository = new RamUserRepo();

    /**
     * Provides an implementation of a user repository
     * @return repository singleton
     */
    public static TrackerUserRepository getRepository() {
        return userRepository;
    }
}
