package website.ylab.financetracker.commands;

import website.ylab.financetracker.transactions.TransactionDataInput;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Contains a list of actions performed on a transaction.
 */
public enum TransactionCommand {
    CREATE {
        @Override
        String execute(String[] args) {
            return dataInput.createNewTransaction();
        }
    },
    UPDATE {
        @Override
        String execute(String[] args) {
            return dataInput.updateTransaction();
        }
    },
    DELETE {
        @Override
        String execute(String[] args) {
            return dataInput.deleteTransaction();
        }
    },
    SHOW {
        @Override
        String execute(String[] args) {
            return dataInput.getTransactions();
        }
    };

    private static final TransactionDataInput dataInput = new TransactionDataInput();

    private static final Map<String, TransactionCommand> stringToEnum = Stream.of(values()).collect(
            toMap(Object::toString, e->e));

    /**
     * Converts a string to an enumeration element.
     * @param s String representation of the enumeration. Can be either upper or lower case.
     * @return Returns an Optional with the enumeration object, or empty if no such object is found.
     */
    public static Optional<TransactionCommand> fromString(String s) {
        return Optional.ofNullable(stringToEnum.get(s.toUpperCase()));
    }

    abstract String execute(String[] args);

    public static String runUserCommand(String[] args) {
        if (Objects.isNull(args) || args.length == 1) {
            return "No required arguments";
        }
        Optional<TransactionCommand> optionalCommand = fromString(args[1].toUpperCase());
        return optionalCommand.isPresent() ? optionalCommand.get().execute(args) : "Bad command";
    }
}