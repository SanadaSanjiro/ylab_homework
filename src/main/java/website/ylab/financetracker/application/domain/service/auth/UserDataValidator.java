package website.ylab.financetracker.application.domain.service.auth;

import website.ylab.financetracker.application.port.in.auth.EmailCheckUseCase;
import website.ylab.financetracker.application.port.in.auth.NameCheckUseCase;

public class UserDataValidator implements NameCheckUseCase, EmailCheckUseCase {
    private final UserDAO userDAO;

    public UserDataValidator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * method to check email uniqueness
     * @param email  String email address to check
     * @return true if the email is not already taken
     */
    @Override
    public boolean isUniqueEmail(String email) {
        return userDAO.getAllUsers().stream().noneMatch(user
                -> user.getEmail().equals(email.toLowerCase()));
    }

    /**
     * method to check username uniqueness
     * @param username String username to check
     * @return true if the username is not already taken
     */
    @Override
    public boolean isUniqueName(String username) {
        return userDAO.getAllUsers().stream().noneMatch(user
                -> user.getUsername().equals(username.toLowerCase()));
    }
}