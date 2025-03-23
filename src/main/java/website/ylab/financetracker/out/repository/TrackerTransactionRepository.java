package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.transactions.TrackerTransaction;

import java.util.List;
import java.util.Optional;

public interface TrackerTransactionRepository {
    Optional<TrackerTransaction> create(TrackerTransaction transaction);
    Optional<TrackerTransaction> update(TrackerTransaction transaction);
    Optional<TrackerTransaction> delete(TrackerTransaction transaction);
    Optional<TrackerTransaction> getById(long id);
    List<TrackerTransaction> getByUserId(long userid);
    List<TrackerTransaction> getAllTransactions();
}