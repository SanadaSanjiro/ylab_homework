package website.ylab.financetracker.adapter.in.transaction;

import website.ylab.financetracker.transactions.TransactionType;

import java.util.Date;

public interface FilterInterface {
    /**
     * Should return TransactionType for transaction data adapters.
     * @return TransactionType
     */
    TransactionType getTypeFilter();

    /**
     * Should return category in String format to be applied as filter
     * @return String category
     */
    String getCategoryFilter();

    /**
     * Should return date to be applied as filter
     * @return Date
     */
    Date getDateFilter();
}
