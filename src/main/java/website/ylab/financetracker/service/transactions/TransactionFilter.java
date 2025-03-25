package website.ylab.financetracker.service.transactions;

import website.ylab.financetracker.in.dto.transaction.TransactionResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Provides static methods for filtering transaction lists
 */
public class TransactionFilter {
    /**
     * Filters transactions by date
     * @param list transaction list
     * @param dateFilter Required transaction date. If this parameter is null, filtering will be skipped.
     * @return filtered transaction list
     */
    public static List<TransactionResponse> filter(List<TransactionResponse> list, Date dateFilter) {
        if (Objects.isNull(dateFilter)) { return list; }
        return list.stream().filter(t->t.getDate().compareTo(dateFilter)==0).toList();
    }

    /**
     * Filters transactions by category
     * @param list transaction list
     * @param categoryFilter Required category in String format. If this parameter is null, filtering will be skipped.
     * @return filtered transaction list
     */
    public static List<TransactionResponse> filter(List<TransactionResponse> list, String categoryFilter) {
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
    public static List<TransactionResponse> filter(List<TransactionResponse> list, TransactionType typeFilter) {
        if (Objects.isNull(typeFilter)) {
            return list;
        }
        return list.stream().filter(t -> t.getType().equals(typeFilter.toString())).toList();
    }
}