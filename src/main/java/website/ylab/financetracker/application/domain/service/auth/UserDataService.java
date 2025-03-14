package website.ylab.financetracker.application.domain.service.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.in.auth.ChangeUseCase;
import website.ylab.financetracker.application.port.in.auth.DeleteUseCase;

import java.util.Optional;

public class UserDataService implements ChangeUseCase, DeleteUseCase {
    private final UserDAO userDAO;
    private final UserDataValidator validator;

    public UserDataService(UserDAO userDAO, UserDataValidator validator) {
        this.userDAO = userDAO;
        this.validator = validator;
    }

    @Override
    public UserModel changeUser(UserModel model) {
        Optional<UserModel> storedUserOptional = userDAO.getUserById(model.getId());
        if (storedUserOptional.isEmpty()) return null;
        UserModel storedUser = storedUserOptional.get();
        String name = storedUser.getUsername();
        String email = storedUser.getEmail();
        String password = storedUser.getPassword();
        if (!model.getUsername().isEmpty()) {
            if(!validator.isUniqueName(model.getUsername())) {
                return null;
            } else {
                name = model.getUsername();
            }
        }
        if (!model.getEmail().isEmpty()) {
            if (!validator.isUniqueEmail(model.getEmail())) {
                return null;
            } else {
                email = model.getEmail();
            }
        }
        if (!model.getPassword().isEmpty()) {
            password = model.getPassword();
        }
        Optional<UserModel> optional = userDAO.changeUser(
                storedUser.setUsername(name).setEmail(email).setPassword(password));
        return optional.orElse(null);
    }

    @Override
    public UserModel deleteUser(long id) {
        Optional<UserModel> storedUserOptional = userDAO.getUserById(id);
        if (storedUserOptional.isEmpty()) return null;
        return userDAO.deleteUser(id).orElse(null);
    }
}