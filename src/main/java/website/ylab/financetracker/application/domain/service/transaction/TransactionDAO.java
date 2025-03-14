package website.ylab.financetracker.application.domain.service.transaction;

import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;
import website.ylab.financetracker.application.port.out.transaction.ExternalTransactionStorage;

import java.util.List;
import java.util.Optional;

public class TransactionDAO {
    private final ExternalTransactionStorage storage;

    public TransactionDAO(ExternalTransactionStorage storage) {
        this.storage = storage;
    }

    /**
     * Transaction creation operation
     * @param transaction TransactionModel for creation
     * @return Optional<TransactionModel> with a new transaction or empty Optional if the operation failed
     */
    public Optional<TransactionModel> create(TransactionModel transaction) {
        return storage.create(transaction);
    }

    /**
     * Gets transaction by id
     * @param id long transaction's number
     * @return Optional<TransactionModel> if successful or empty Optional if no transaction with that id was found
     */
    public Optional<TransactionModel> getById(Long id) {
        return storage.getById(id);
    }

    /**
     * Transaction data update operation
     * @param transaction TransactionModel with new parameters to store
     * @return Optional<TransactionModel> with new values if successful,
     * or empty Optional if the operation failed
     */
    public Optional<TransactionModel> update(TransactionModel transaction) {
        return storage.update(transaction);
    }

    /**
     * Delete transaction by id.
     * @param id long transaction's number
     * @return Optional<TransactionModel> if successful
     * or empty Optional if no transaction with that ID was found to delete.
     */
    public Optional<TransactionModel> delete(long id) {
        return storage.delete(id);
    }

    /**
     * Returns all transactions
     * @return List<TransactionModel> with all saved transactions, or an empty list if none found
     */
    public List<TransactionModel> getAll() {
        return storage.getAll();
    }

    /**
     * Provides all transactions of the user.
     * @return list of transactions of the user
     */
    List<TransactionModel> getByUser(long userId) { return storage.getByUser(userId); }
}
