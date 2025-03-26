package website.ylab.financetracker.service.transactions;

import website.ylab.financetracker.in.dto.transaction.TransactionMapper;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.out.repository.TrackerTransactionRepository;

import java.util.*;

/**
 * Provides methods for changing transaction data.
 */
public class TransactionService {
    private final TrackerTransactionRepository repository;
    private final TransactionMapper mapper = TransactionMapper.INSTANCE;

    public TransactionService(TrackerTransactionRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new transaction. Expects non-empty and valid values for type, amount, category, date, and description.
     * @param transaction TrackerTransaction with filled fields
     * @return TransactionResponse if success or null if failed
     */
    public TransactionResponse addNewTransaction (TrackerTransaction transaction) {
        transaction.setUuid(UUID.randomUUID().toString());
        Optional<TrackerTransaction> optional = repository.create(transaction);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * Changes amount, category, date and description. The input must be not empty and completely valid.
     * @param transaction TrackerTransaction object with non-empty valid amount, category, date and description.
     * @return TransactionResponse if success or null if failed
     */
    public TransactionResponse changeTransaction (TrackerTransaction transaction) {
        Optional<TrackerTransaction> optional = repository.getById(transaction.getId());
        if (optional.isEmpty()) {
            return null;
        }
        TrackerTransaction storedTransaction = optional.get();
        storedTransaction.setAmount(transaction.getAmount());
        storedTransaction.setCategory(transaction.getCategory());
        storedTransaction.setDescription(transaction.getDescription());
        optional = repository.update(storedTransaction);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * Deletes a transaction by its ID
     * @param id long transactions id
     * @return TransactionResponse with deleted transaction if success or null if failed
     */
    public TransactionResponse deleteTransaction (long id) {
        Optional<TrackerTransaction> optional = repository.getById(id);
        if (optional.isEmpty()) {
            return null;
        }
        TrackerTransaction transaction = optional.get();
        optional = repository.delete(transaction);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * Deletes all transactions of the selected user.
     * @param userId long
     */
    public List<TransactionResponse> deleteUserTransactions(long userId) {
        List<TrackerTransaction> transactions = repository.getByUserId(userId);
        List<TransactionResponse> result = new ArrayList<>();
        for (TrackerTransaction transaction: transactions) {
            Optional<TrackerTransaction> optional = repository.delete(transaction);
            optional.ifPresent(trackerTransaction -> result.add(mapper.toResponse(trackerTransaction)));
        }
        return result;
    }

    /**
     * Provides all transactions from storage
     * @return list of transactions
     */
    public List<TransactionResponse> getAllTransactions() {
        return mapper.toTransactionResponseList(repository.getAllTransactions());
    }

    /**
     * Provides all transactions of the user.
     * @param userId long user's id
     * @return  List<TransactionResponse>
     */
    public List<TransactionResponse> getUserTransaction(long userId) {
        return mapper.toTransactionResponseList(repository.getByUserId(userId));
    }

    /**
     * Get a transactions by id.
     * @param id long transaction's id
     * @return TransactionResponse
     */
    public TransactionResponse getById(long id) {
        Optional<TrackerTransaction> optional = repository.getById(id);
        return optional.map(mapper::toResponse).orElse(null);
    }

    public List<TransactionResponse> getFiltered(TrackerTransaction transaction) {
        Date dateFilter = transaction.getDate();
        String categoryFilter = transaction.getCategory();
        TransactionType typeFilter = transaction.getType();
        List<TransactionResponse> result = getUserTransaction(transaction.getUserId());
        result = TransactionFilter.filter(result, dateFilter);
        result = TransactionFilter.filter(result, typeFilter);
        result = TransactionFilter.filter(result, categoryFilter);
        return result;
    }
}