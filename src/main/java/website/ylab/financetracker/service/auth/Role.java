package website.ylab.financetracker.service.auth;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * User Roles
 */
public enum Role {
    ADMIN,
    USER;

    private static final Map<String, Role> stringToEnum = Stream.of(values()).collect(
            toMap(Object::toString, e->e));

    /**
     * Converts a string to an enumeration element.
     * @param s String representation of the enumeration. Can be either upper or lower case.
     * @return Returns an Optional with the enumeration object, or empty if no such object is found.
     */
    public static Optional<Role> fromString(String s) {
        return Optional.ofNullable(stringToEnum.get(s.toUpperCase()));
    }
}
