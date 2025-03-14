package website.ylab.financetracker.application.port.in.transaction;

import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;
import website.ylab.financetracker.transactions.TransactionType;

import java.util.Date;
import java.util.List;

/**
 * Transaction operations
 */
public interface TransactionCrudUseCase {
    /**
     * Register new transaction
     * @param transactionModel TransactionModel with not empty type, amount, category and date
     * @return TransactionModel with id and user id if success or null if creation error occurred
     */
    TransactionModel create(TransactionModel transactionModel);

    /**
     * Delete exiting transaction
     * @param id transaction id
     * @return TransactionModel of deleted user if success or null if error occurred
     */
    TransactionModel delete(long id);

    /**
     * Returns filtered list of transactions
     * @param dateFilter Date to show the transaction on that date, or null to skip this filter
     * @param categoryFilter A string with the name of a category to display the transaction for that category,
     *                       or null to skip this filter
     * @param typeFilter TransactionType to display transactions of this type, or null to skip this filter
     * @return List<TransactionModel> filtered list of transactions or all transactions if all filters were empty
     */
    List<TransactionModel> show(Date dateFilter, String categoryFilter, TransactionType typeFilter);

    /**
     * Update existing transaction
     * @param transactionModel TransactionModel with non-empty id, and new values amount, category, and description.
     *                         If they are missing, the values are not changed.
     * @return TransactionModel with updated transaction if success or null if creation error occurred
     */
    TransactionModel update(TransactionModel transactionModel);
}