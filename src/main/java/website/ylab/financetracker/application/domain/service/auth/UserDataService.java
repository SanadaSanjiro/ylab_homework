package website.ylab.financetracker.application.domain.service.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.in.auth.ChangeUseCase;
import website.ylab.financetracker.application.port.in.auth.DeleteUseCase;

import java.util.List;
import java.util.Optional;

public class UserDataService implements ChangeUseCase, DeleteUseCase {
    private final UserDAO userDAO;
    private final UserDataValidator validator;
    private final UserDataRemovingService userDataRemovingService;

    public UserDataService(UserDAO userDAO, UserDataValidator validator, UserDataRemovingService userDataRemovingService) {
        this.userDAO = userDAO;
        this.validator = validator;
        this.userDataRemovingService = userDataRemovingService;
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
        Optional<UserModel> optional = userDAO.updateUser(
                storedUser.setUsername(name).setEmail(email).setPassword(password));
        return optional.orElse(null);
    }

    @Override
    public UserModel deleteUser(long id) {
        Optional<UserModel> storedUserOptional = userDAO.getUserById(id);
        if (storedUserOptional.isEmpty()) return null;
        userDataRemovingService.removeUserData(storedUserOptional.get());
        return userDAO.deleteUser(id).orElse(null);
    }

    public Optional<UserModel> blockUser(long id) {
        Optional<UserModel> optional = userDAO.getUserById(id);
        if (optional.isEmpty()) return Optional.empty();
        UserModel user = optional.get();
        user.setEnabled(false);
        return userDAO.updateUser(user);
    }

    public Optional<UserModel> unblockUser(long id) {
        Optional<UserModel> optional = userDAO.getUserById(id);
        if (optional.isEmpty()) return Optional.empty();
        UserModel user = optional.get();
        user.setEnabled(true);
        return userDAO.updateUser(user);
    }

    public Optional<UserModel> getUser(long id) {
        return userDAO.getUserById(id);
    }

    /**
     * Changes all data of stored user
     * @param model UserModel must be a user with all required fields filled in.
     * @return Optional<UserModel> with updated user or empty if no user found to update
     */
    public Optional<UserModel> updateUser(UserModel model) {
        return userDAO.updateUser(model);
    }

    public List<UserModel> getAllUsers() {
        return userDAO.getAllUsers();
    }
}