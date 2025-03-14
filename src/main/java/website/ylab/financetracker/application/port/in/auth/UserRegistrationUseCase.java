package website.ylab.financetracker.application.port.in.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;

/**
 * Register new user
 */
public interface UserRegistrationUseCase {
    /**
     * Register new user
     * @param user UserModel with not empty. correct and unique username, email and non-blanc password
     * @return UserModel with id if success or null if creation error occurred
     */
    UserModel registerNewUser(UserModel user);
}