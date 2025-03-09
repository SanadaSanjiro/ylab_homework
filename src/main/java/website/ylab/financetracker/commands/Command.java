package website.ylab.financetracker.commands;

import website.ylab.financetracker.auth.LoginDataInput;
import website.ylab.financetracker.auth.UserDataInput;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Contains a list of available commands.
 */
public enum Command {
    SIGNIN {
        @Override
        String execute(String[] args) {
            return UserDataInput.createNewUser();
        }
    },
    LOGIN {
        @Override
        String execute(String[] args) {
            return LoginDataInput.login();
        }
    },
    USER {
        @Override
        String execute(String[] args) {
            return UserCommand.runUserCommand(args);
        }
    },
    TRANSACTION {
        @Override
        String execute(String[] args) {
            return TransactionCommand.runUserCommand(args);
        }
    },
    BUDGET {
        @Override
        String execute(String[] args) {
            return BudgetCommand.runUserCommand(args);
        }
    },
    TARGET {
        @Override
        String execute(String[] args) {
            return TargetCommand.runUserCommand(args);
        }
    },
    STAT {
        @Override
        String execute(String[] args) {
            return StatCommand.runUserCommand(args);
        }
    },
    API {
        @Override
        String execute(String[] args) {
            return ApiCommand.runUserCommand(args);
        }
    },
    ADM {
        @Override
        String execute(String[] args) {
            return "Adm";
        }
    };

    // Используется для преобразования строк в экземпляры перечисления
    private static final Map<String, Command> stringToEnum = Stream.of(values()).collect(
            toMap(Object::toString, e->e));
    /**
     * Converts a string to an enumeration element.
     * @param s String representation of the enumeration. Can be either upper or lower case.
     * @return Returns an Optional with the enumeration object, or empty if no such object is found.
     */
    public static Optional<Command> fromString(String s) {
        return Optional.ofNullable(stringToEnum.get(s.toUpperCase()));
    }

    abstract String execute(String[] args);

    public static String runCommand(String[] args) {
        if (Objects.isNull(args) || args.length == 0) {
            return "No command";
        }
        Optional<Command> optionalCommand = fromString(args[0].toUpperCase());
        return optionalCommand.isPresent() ? optionalCommand.get().execute(args) : "Bad command";
    }
}