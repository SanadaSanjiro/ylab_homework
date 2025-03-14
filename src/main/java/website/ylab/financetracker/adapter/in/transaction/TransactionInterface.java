package website.ylab.financetracker.adapter.in.transaction;

import website.ylab.financetracker.transactions.TransactionType;

import java.util.Date;

/**
 * Pack of methods to get transactions data
 */
public interface TransactionInterface {
    /**
     * Should return TransactionType for transaction data adapters.
     * @return TransactionType
     */
    TransactionType getType ();

    /**
     * Should return amount in double
     * @return correct not-null double value
     */
    double getAmount();

    /**
     * Should return category in String format for transaction data adapters.
     * @return String category
     */
    String getCategory();

    /**
     * Should return Date for transaction data adapters.
     * @return Date
     */
    Date getDate();

    /**
     * Should return description in String format for transaction data adapters.
     * @return String description
     */
    String getDescription();

    /**
     * Should return transaction id
     * @return long transaction id
     */
    long getId();

    /**
     * Used to get a different users inputs. Should display incoming message and return users input
     * @param message Message in String format, that should be displayed for a user
     * @return user's input in String format
     */

    String getData(String message);
}
