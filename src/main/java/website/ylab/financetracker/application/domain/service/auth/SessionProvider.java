package website.ylab.financetracker.application.domain.service.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.in.auth.LoginUseCase;
import website.ylab.financetracker.application.port.in.auth.LogoutUseCase;

import java.util.Optional;

public class SessionProvider implements LoginUseCase, LogoutUseCase {
    private UserModel currentUser = null;
    private final UserDAO userDAO;

    public SessionProvider(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }

    @Override
    public UserModel login(UserModel userData) {
        Optional<UserModel> optional = userDAO.getUserByName(userData.getUsername());
        if (optional.isEmpty()) return null;
        UserModel user = optional.get();
        if (user.getPassword().equals(userData.getPassword())) {
            this.currentUser = user;
            return user;
        }
        return null;
    }

    @Override
    public UserModel logout() {
        UserModel user = currentUser;
        currentUser = null;
        return user;
    }
}