package website.ylab.financetracker.commands;

import website.ylab.financetracker.auth.UserDataInput;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Contains a list of actions performed on a user account.
 */

public enum UserCommand {
    CHANGE {
        @Override
        String execute(String[] args) {
            return UserDataInput.changeUserData();
        }
    },
    DELETE {
        @Override
        String execute(String[] args) {
            return UserDataInput.deleteUser();
        }
    };

    private static final Map<String, UserCommand> stringToEnum = Stream.of(values()).collect(
            toMap(Object::toString, e->e));

    /**
     * Converts a string to an enumeration element.
     * @param s String representation of the enumeration. Can be either upper or lower case.
     * @return Returns an Optional with the enumeration object, or empty if no such object is found.
     */
    public static Optional<UserCommand> fromString(String s) {
        return Optional.ofNullable(stringToEnum.get(s.toUpperCase()));
    }

    abstract String execute(String[] args);

    public static String runUserCommand(String[] args) {
        if (Objects.isNull(args) || args.length == 1) {
            return "No required arguments";
        }
        Optional<UserCommand> optionalCommand = fromString(args[1].toUpperCase());
        return optionalCommand.isPresent() ? optionalCommand.get().execute(args) : "Bad command";
    }
}