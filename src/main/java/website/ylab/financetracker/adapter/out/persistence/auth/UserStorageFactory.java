package website.ylab.financetracker.adapter.out.persistence.auth;

import website.ylab.financetracker.application.port.out.auth.ExternalUserStorage;

public class UserStorageFactory {
    private static final ExternalUserStorage userStorage = getUserStorage();

    public static ExternalUserStorage getStorage() {
        return userStorage;
    }

    private static ExternalUserStorage getUserStorage() {
        UserEntityMapper mapper = new UserEntityMapper();
        UserRepository repository = new UserRepositoryImplementation();
        return new UserPersistenceAdapter(repository, mapper);
    }
}