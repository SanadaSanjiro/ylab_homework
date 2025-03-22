package website.ylab.financetracker.transactions;

import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.auth.UserAuthService;
import website.ylab.financetracker.out.persistence.TrackerTransactionRepository;

import java.util.*;

/**
 * Provides methods for changing transaction data.
 */
public class TransactionService {
    private final TrackerTransactionRepository repository;

    public TransactionService(TrackerTransactionRepository repository) {
        this.repository = repository;
    }

    public String addNewTransaction (TransactionType type,
                                     double amount,
                                     String category,
                                     Date date,
                                     String description) {
        TrackerTransaction transaction = new TrackerTransaction();
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDate(date);
        transaction.setDescription(description);
        transaction.setUserId(UserAuthService.getCurrentUser().getId());
        transaction.setUuid(UUID.randomUUID().toString());
        Optional<TrackerTransaction> optional = repository.create(transaction);
        if (optional.isPresent()) {
            return "Transaction added successfully";
        } else {
            return "Transaction creation error";
        }
    }


    public String changeTransaction (long id, double newAmount, String newCategory, String newDescription) {
        Optional<TrackerTransaction> optional = repository.getById(id);
        if (optional.isEmpty()) {
            return "Transaction not found";
        }
        TrackerTransaction oldTransaction = optional.get();
        if (oldTransaction.getUserId()!=UserAuthService.getCurrentUser().getId()) {
            return "You do not have permission to change this transaction";
        }
        oldTransaction.setAmount(newAmount);
        oldTransaction.setCategory(newCategory);
        oldTransaction.setDescription(newDescription);
        repository.update(oldTransaction);
        return "Transaction data successfully changed";
    }

    public String deleteTransaction (long id) {
        Optional<TrackerTransaction> optional = repository.getById(id);
        if (optional.isEmpty()) {
            return "Transaction not found";
        }
        TrackerTransaction transaction = optional.get();
        if (transaction.getUserId()!=UserAuthService.getCurrentUser().getId()) {
            return "You do not have permission to delete this transaction";
        }
        repository.delete(optional.get());
        return "Transaction successfully deleted";
    }

    /**
     * Deletes all transactions of the selected user.
     * @param user TransactionUser
     */
    public void deleteUserTransactions(TrackerUser user) {
        List<TrackerTransaction> transactions = repository.getAllTransactions()
                .stream().filter(t->t.getUserId()==user.getId())
                .toList();
        transactions.forEach(repository::delete);
    }

    /**
     * Provides all transactions for the current user.
     * @return list of transactions for the current user
     */
    public List<TrackerTransaction> getAllTransactions() {
        TrackerUser user = UserAuthService.getCurrentUser();
        if (Objects.isNull(user)) {
            return new ArrayList<>();
        }
        return repository.getByUserId(user.getId());
    }

    /**
     * Provides all transactions of the user.
     * @return list of transactions of the user
     */
    public List<TrackerTransaction> getUserTransaction(TrackerUser user) {
        return repository.getByUserId(user.getId());
    }
}