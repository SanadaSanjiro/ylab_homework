package website.ylab.financetracker.transactions;

import java.util.List;
import java.util.Optional;

public interface TrackerTransactionRepository {
    public Optional<TrackerTransaction> create(TrackerTransaction transaction);
    public Optional<TrackerTransaction> update(TrackerTransaction transaction);
    public Optional<TrackerTransaction> delete(TrackerTransaction transaction);
    public Optional<TrackerTransaction> get(long id);
    public List<TrackerTransaction> getAllTransactions();
}