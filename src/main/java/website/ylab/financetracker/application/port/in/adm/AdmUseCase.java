package website.ylab.financetracker.application.port.in.adm;

import website.ylab.financetracker.application.domain.model.auth.Role;
import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;

import java.util.List;

/**
 * Provides methods for administrative operations.
 * All methods must be run by a user with the ADMIN role, otherwise they do nothing.
 */
public interface AdmUseCase {
    /**
     * Get all users. Must be run by a user with the ADMIN role, otherwise do nothing.
     * @return List<UserModel> all users registered in the system
     */
    List<UserModel> getUsers();

    /**
     * Get all user's transactions. Must be run by a user with the ADMIN role, otherwise do nothing.
     * @param userId id of the user to get transactions
     * @return all transaction of selected user, or empty list if nothing found
     */
    List<TransactionModel> getUserTransactions(long userId);

    /**
     * Blocks a user. Prevents this user from logging in, but all of their data remains in the system.
     * @param userId the user ID to block
     * @return UserModel blocked user or null if user not found
     */
    UserModel blockUser(long userId);

    /**
     * Unblocks the user so he can now log in.
     * @param userId the user ID to unblock
     * @return UserModel unblocked user or null if user not found
     */
    UserModel unblockUser(long userId);

    /**
     * Completely removes the user and all his data from the system
     * @param userId the user ID to delete
     * @return UserModel deleted user or null if user not found
     */
    UserModel deleteUser(long userId);

    /**
     * Sets a new role for a user
     * @param role new user's Role
     * @param userId user's id
     * @return UserModel of user with a new role or null if user not found
     */
    UserModel changeUserRole(Role role, long userId);

    /**
     * Checks if the current user has administrator rights.
     * @return boolean true if the current user has the ADMIN role
     */
    boolean isAdmin();
}