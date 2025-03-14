package website.ylab.financetracker.application.port.out.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;

import java.util.List;
import java.util.Optional;

/**
 * CRUD operations with users
 */
public interface ExternalUserStorage {
    /**
     * User creation operation
     * @param user UserModel for creation
     * @return Optional<UserModel> with a new user or empty Optional if the operation failed
     */
    Optional<UserModel> create(UserModel user);

    /**
     * Gets user by id
     * @param id long user id
     * @return Optional<UserModel> if successful or empty Optional if no user with that id was found
     */
    Optional<UserModel> getById(long id);

    /**
     * Gets user by name
     * @param username String username
     * @return Optional<UserModel> if successful or empty Optional if no user with that name was found
     */
    Optional<UserModel> findByName(String username);

    /**
     * User data update operation
     * @param newUser UserModel with new parameters to store
     * @return Optional<UserModel> with new values if successful,
     * or empty Optional if the operation failed
     */
    Optional<UserModel> update(UserModel newUser);

    /**
     * Deleting a user. Please note that all user data must also be deleted!
     * @param id long user id
     * @return Optional<UserModel> if successful
     * or empty Optional if no user with that ID was found to delete.
     */
    Optional<UserModel> delete(long id);

    /**
     * Returns all users
     * @return List<UserModel> with all users, or an empty list if none found
     */
    List<UserModel> getAllUsers();
}