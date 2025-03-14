package website.ylab.financetracker.commands;

import website.ylab.financetracker.budget.BudgetDataInput;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Contains a list of actions performed on a budget.
 */
public enum BudgetCommand {
    SET {
        @Override
        String execute(String[] args) {
            return dataInput.setBudget();
        }
    },
    SHOW {
        @Override
        String execute(String[] args) {
            return dataInput.getBudget();
        }
    };

    private static final BudgetDataInput dataInput = new BudgetDataInput();

    private static final Map<String, BudgetCommand> stringToEnum = Stream.of(values()).collect(
            toMap(Object::toString, e->e));

    /**
     * Converts a string to an enumeration element.
     * @param s String representation of the enumeration. Can be either upper or lower case.
     * @return Returns an Optional with the enumeration object, or empty if no such object is found.
     */
    public static Optional<BudgetCommand> fromString(String s) {
        return Optional.ofNullable(stringToEnum.get(s.toUpperCase()));
    }

    abstract String execute(String[] args);

    public static String runUserCommand(String[] args) {
        if (Objects.isNull(args) || args.length == 1) {
            return "No required arguments";
        }
        Optional<BudgetCommand> optionalCommand = fromString(args[1].toUpperCase());
        return optionalCommand.isPresent() ? optionalCommand.get().execute(args) : "Bad command";
    }
}