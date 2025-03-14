package website.ylab.financetracker.application.domain.service.transaction;

import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;
import website.ylab.financetracker.transactions.TransactionType;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Provides methods for filtering transaction lists
 */
public class TransactionFilterer {
    /**
     * Filters transactions by date
     * @param list transaction list
     * @param dateFilter Required transaction date. If this parameter is null, filtering will be skipped.
     * @return filtered transaction list
     */
    public List<TransactionModel> filter(List<TransactionModel> list, Date dateFilter) {
        if (Objects.isNull(dateFilter)) { return list; }
        return list.stream().filter(t->t.getDate().equals(dateFilter)).toList();
    }

    /**
     * Filters transactions by category
     * @param list transaction list
     * @param categoryFilter Required category in String format. If this parameter is null, filtering will be skipped.
     * @return filtered transaction list
     */
    public List<TransactionModel> filter(List<TransactionModel> list, String categoryFilter) {
        if (Objects.isNull(categoryFilter)) {
            return list;
        }
        return list.stream().filter(t -> t.getCategory().equals(categoryFilter)).toList();
    }

    /**
     * Filters transactions by type
     * @param list transaction list
     * @param typeFilter TransactionType. If this parameter is null, filtering will be skipped.
     * @return filtered transaction list
     */
    public List<TransactionModel> filter(List<TransactionModel> list, TransactionType typeFilter) {
        if (Objects.isNull(typeFilter)) {
            return list;
        }
        return list.stream().filter(t -> t.getType().equals(typeFilter)).toList();
    }
}