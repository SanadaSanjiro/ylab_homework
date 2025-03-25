package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.auth.TrackerUser;

import java.util.List;
import java.util.Optional;

/**
 * Describes methods for working with user entities
 * Repository implementation must create admin user with role=Role.ADMIN, name=admin, password=123, isEnabled = true
 * and non-empty email
 */
public interface TrackerUserRepository {
    /**
     * Puts a new user into storage
     * @param user TrackerUser nes user
     * @return Optional<TrackerUser> on success or an empty Optional on failure
     */
    Optional<TrackerUser> create(TrackerUser user);

    /**
     * Gets a user by their name
     * @param username String username
     * @return Optional<TrackerUser> on success, or an empty Optional if nothing was found
     */
    Optional<TrackerUser> getByName(String username);

    /**
     * Gets a user by their ID
     * @param id long user's ID
     * @return Optional<TrackerUser> on success, or an empty Optional if nothing was found
     */
    Optional<TrackerUser> getById(long id);

    /**
     * Modifies an existing user's data
     * @param user TrackerUser new user data with the ID of the user that needs to be changed
     * @return Optional<TrackerUser> on success or an empty Optional on failure
     */
    Optional<TrackerUser> update(TrackerUser user);

    /**
     * Deletes user from a storage
     * @param user TrackerUser user to be deleted
     * @return Optional<TrackerUser> on success or an empty Optional on failure
     */
    Optional<TrackerUser> delete(TrackerUser user);

    /**
     * Gets a List with all transactions
     * @return List<TrackerUser> on success or an empty List on failure
     */
    List<TrackerUser> getAllUsers();
}