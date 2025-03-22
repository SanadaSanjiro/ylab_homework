package website.ylab.financetracker.commands;

import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.auth.UserAuthService;
import website.ylab.financetracker.budget.BudgetDataInput;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Contains a list of actions performed via API.
 */
public enum ApiCommand {
    EXCEEDANCE {
        @Override
        String execute(String[] args) {
            TrackerUser user = UserAuthService.getCurrentUser();
            if (Objects.isNull(user)) return "You should log in first";
            return Boolean.valueOf(dataInput.isExceedBudget()).toString();
        }
    },
    EMAILS {
        @Override
        String execute(String[] args) {
            TrackerUser user = UserAuthService.getCurrentUser();
            if (Objects.isNull(user)) return "You should log in first";
            return dataInput.getEmailNotifications().toString();
        }
    };

    private static final BudgetDataInput dataInput = new BudgetDataInput();

    private static final Map<String, ApiCommand> stringToEnum = Stream.of(values()).collect(
            toMap(Object::toString, e->e));

    /**
     * Converts a string to an enumeration element.
     * @param s String representation of the enumeration. Can be either upper or lower case.
     * @return Returns an Optional with the enumeration object, or empty if no such object is found.
     */
    public static Optional<ApiCommand> fromString(String s) {
        return Optional.ofNullable(stringToEnum.get(s.toUpperCase()));
    }

    abstract String execute(String[] args);

    public static String runUserCommand(String[] args) {
        if (Objects.isNull(args) || args.length == 1) {
            return "No required arguments";
        }
        Optional<ApiCommand> optionalCommand = fromString(args[1].toUpperCase());
        return optionalCommand.isPresent() ? optionalCommand.get().execute(args) : "Bad command";
    }
}