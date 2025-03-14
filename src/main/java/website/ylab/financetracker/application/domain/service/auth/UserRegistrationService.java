package website.ylab.financetracker.application.domain.service.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.in.auth.UserRegistrationUseCase;
import website.ylab.financetracker.application.domain.model.auth.Role;

import java.util.Optional;

public class UserRegistrationService implements UserRegistrationUseCase {
    private final UserDAO userDAO;
    private final UserDataValidator validator;

    public UserRegistrationService(UserDAO userDAO, UserDataValidator validator) {
        this.userDAO = userDAO;
        this.validator = validator;
        if (userDAO.getUserByName("admin").isEmpty()) {
            userDAO.createUser(getAdmin());
        }
    }

    @Override
    public UserModel registerNewUser(UserModel userModel) {
        if (!validator.isUniqueName(userModel.getUsername())) {
            return null;
        }
        if (!validator.isUniqueEmail(userModel.getEmail())) {
            return null;
        }
        Optional<UserModel> optional = userDAO.createUser(userModel);
        return optional.orElse(null);
    }

    private UserModel getAdmin() {
        UserModel admin = new UserModel();
        admin.setUsername("admin");
        admin.setEmail("admin@admin.com");
        admin.setPassword("123");
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);
        return admin;
    }
}