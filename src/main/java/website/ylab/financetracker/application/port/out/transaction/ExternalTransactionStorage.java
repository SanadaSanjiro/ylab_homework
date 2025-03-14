package website.ylab.financetracker.application.port.out.transaction;

import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;

import java.util.List;
import java.util.Optional;

/**
 * CRUD operations with transactions
 */
public interface ExternalTransactionStorage {
    /**
     * Create transaction operation
     * @param transaction TransactionModel for creation
     * @return Optional<TransactionModel> with a new transaction or empty Optional if the operation failed
     */
    Optional<TransactionModel> create(TransactionModel transaction);

    /**
     * Get transaction by id
     * @param id long transaction's number
     * @return Optional<TransactionModel> if successful or empty Optional if no transaction with that id was found
     */
    Optional<TransactionModel> getById(long id);

    /**
     * Transaction data update operation
     * @param transaction TransactionModel with new parameters to store
     * @return Optional<TransactionModel> with new values if successful,
     * or empty Optional if the operation failed
     */
    Optional<TransactionModel> update(TransactionModel transaction);

    /**
     * Deleting a transaction
     * @param id long transaction's number
     * @return Optional<TransactionModel> if successful
     *  or empty Optional if no transaction with that ID was found to delete.
     */
    Optional<TransactionModel> delete(long id);

    /**
     * Returns all transactions
     * @return List<TransactionModel> with all saved transactions, or an empty list if none found
     */
    List<TransactionModel> getAll();

    /**
     * Returns all transactions by user id
     * @param userId long user id
     * @return List<TransactionModel> with all transactions saved by the selected user,
     * or an empty list if none are found
     */
    List<TransactionModel> getByUser(long userId);
}