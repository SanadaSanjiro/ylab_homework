package website.ylab.financetracker.transactions;

import java.util.List;
import java.util.Optional;

public interface TrackerTransactionRepository {
    Optional<TrackerTransaction> create(TrackerTransaction transaction);
    Optional<TrackerTransaction> update(TrackerTransaction transaction);
    Optional<TrackerTransaction> delete(TrackerTransaction transaction);
    Optional<TrackerTransaction> get(long id);
    List<TrackerTransaction> getAllTransactions();
}