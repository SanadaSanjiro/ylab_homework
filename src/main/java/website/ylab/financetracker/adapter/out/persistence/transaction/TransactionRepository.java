package website.ylab.financetracker.adapter.out.persistence.transaction;

import java.util.List;

public interface TransactionRepository {
    TransactionEntity create(TransactionEntity transaction);
    TransactionEntity getById(long id);
    TransactionEntity delete(long id);
    TransactionEntity update(TransactionEntity transaction);
    List<TransactionEntity> getAll();
    List<TransactionEntity> getByUser(long userId);
}
