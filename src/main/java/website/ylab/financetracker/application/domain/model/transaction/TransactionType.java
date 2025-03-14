package website.ylab.financetracker.application.domain.model.transaction;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Specifies the possible transaction types.
 */
public enum TransactionType {
    INCOME,
    EXPENSE;

    private static final Map<String, TransactionType> stringToEnum = Stream.of(values()).collect(
            toMap(Object::toString, e->e));

    /**
     * Converts a string to an enumeration element.
     * @param s String representation of the enumeration. Can be either upper or lower case.
     * @return Returns an Optional with the enumeration object, or empty if no such object is found.
     */
    public static Optional<TransactionType> fromString(String s) {
        return Optional.ofNullable(stringToEnum.get(s.toUpperCase()));
    }
}
