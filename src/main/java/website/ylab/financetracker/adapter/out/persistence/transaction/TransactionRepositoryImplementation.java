package website.ylab.financetracker.adapter.out.persistence.transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepositoryImplementation implements TransactionRepository {
    private final Map<Long, TransactionEntity> transactions = new HashMap<>();
    private long counter=0L;

    @Override
    public TransactionEntity create(TransactionEntity transaction) {
        transaction.setId(++counter);
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public TransactionEntity getById(long id) {
        return transactions.get(id);
    }

    @Override
    public TransactionEntity update(TransactionEntity transaction) {
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public TransactionEntity delete(long id) {
        return transactions.remove(id);
    }

    @Override
    public List<TransactionEntity> getAll() {
        return transactions.values().stream().toList();
    }

    @Override
    public List<TransactionEntity> getByUser(long userId) {
        return transactions
                .values()
                .stream()
                .filter(v->v.getUserId()==userId)
                .toList();
    }
}