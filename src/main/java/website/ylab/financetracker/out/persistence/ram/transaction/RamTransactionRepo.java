package website.ylab.financetracker.out.persistence.ram.transaction;

import website.ylab.financetracker.out.persistence.TrackerTransactionRepository;
import website.ylab.financetracker.transactions.TrackerTransaction;

import java.util.*;

/**
 * In-memory repository implementation
 */
public class RamTransactionRepo implements TrackerTransactionRepository {
    private final List<TrackerTransaction> storage = new ArrayList<>();
    private static long transacCounter=1L;
    @Override
    public Optional<TrackerTransaction> create(TrackerTransaction transaction) {
            if (!storage.contains(transaction)) {
                transaction.setId(transacCounter++);
                storage.add(transaction);
                return Optional.of(transaction);
            } else {
                return Optional.empty();
            }
    }

    @Override
    public Optional<TrackerTransaction> update(TrackerTransaction transaction) {
        long id = transaction.getId();
        Optional<TrackerTransaction> optional =  getTransaction(id);
        optional.ifPresent(trackerTransaction -> copyTransactionData(trackerTransaction, transaction));
        return optional;
    }

    @Override
    public Optional<TrackerTransaction> delete(TrackerTransaction transaction) {
        long id = transaction.getId();
        Optional<TrackerTransaction> optional =  getTransaction(id);
        optional.ifPresent(storage::remove);
        return optional;
    }

    @Override
    public Optional<TrackerTransaction> getById(long id) {
        return getTransaction(id);
    }

    @Override
    public List<TrackerTransaction> getAllTransactions() {
        return List.copyOf(storage);
    }

    private Optional<TrackerTransaction> getTransaction(long id) {
        return storage
                .stream()
                .filter(trackerTransaction -> trackerTransaction.getId() == id)
                .findFirst();
    }

    @Override
    public List<TrackerTransaction> getByUserId(long userid) {
        return storage.stream().filter(t->t.getUserId()==userid).toList();
    }

    private void copyTransactionData(TrackerTransaction oldTransaction, TrackerTransaction newTransaction) {
        oldTransaction.setAmount(newTransaction.getAmount());
        oldTransaction.setCategory(newTransaction.getCategory());
        oldTransaction.setDescription(newTransaction.getDescription());
    }
}
