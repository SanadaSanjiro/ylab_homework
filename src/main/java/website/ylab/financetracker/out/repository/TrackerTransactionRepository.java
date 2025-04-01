package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.transactions.TrackerTransaction;

import java.util.List;
import java.util.Optional;

/**
 * Describes methods for working with transaction entities
 */
public interface TrackerTransactionRepository {
    /**
     * Puts a new transaction into storage
     * @param transaction TrackerTransaction new transaction
     * @return Optional<TrackerTransaction> on success or an empty Optional on failure
     */
    Optional<TrackerTransaction> create(TrackerTransaction transaction);

    /**
     * Modifies an existing transaction. Amount, category and description can be changed
     * @param transaction TrackerTransaction with new amount, category and description values
     * @return Optional<TrackerTransaction> ion success or an empty Optional on failure
     */
    Optional<TrackerTransaction> update(TrackerTransaction transaction);

    /**
     * Deletes transaction from a storage
     * @param transaction TrackerTransaction to delete
     * @return Optional<TrackerTransaction> on success or an empty Optional on failure
     */
    Optional<TrackerTransaction> delete(TrackerTransaction transaction);

    /**
     * Gets transaction value by its ID
     * @param id long transaction's id
     * @return Optional<TrackerTransaction> on success, or an empty Optional if nothing was found
     */
    Optional<TrackerTransaction> getById(long id);

    /**
     * Gets a list of user transactions by user ID
     * @param userid long user ID
     * @return List<TrackerTransaction> on success, or an empty Optional if nothing was found
     */
    List<TrackerTransaction> getByUserId(long userid);

    /**
     * Gets a list of all saved transactions.
     * @return List<TrackerTransaction> on success, or an empty Optional if nothing was found
     */
    List<TrackerTransaction> getAllTransactions();
}