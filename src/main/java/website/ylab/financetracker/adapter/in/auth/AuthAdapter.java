package website.ylab.financetracker.adapter.in.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.in.auth.LoginUseCase;
import website.ylab.financetracker.application.port.in.auth.LogoutUseCase;

import java.util.Objects;

public class AuthAdapter {
    private final LoginUseCase loginService;
    private final LogoutUseCase logoutService;
    private final UserInterface userInterface;

    public AuthAdapter(LoginUseCase loginService, LogoutUseCase logoutService, UserInterface userInterface) {
        this.loginService = loginService;
        this.logoutService = logoutService;
        this.userInterface = userInterface;
    }

    public String login() {
        UserModel userModel = new UserModel()
                .setUsername(userInterface.getData("Please enter your name."))
                .setPassword(userInterface.getData("Please enter your password."));
        userModel = loginService.login(userModel);
        return Objects.isNull(userModel) ? "login error" : userModel.toString();
    }

    public String logout() {
        UserModel userModel = logoutService.logout();
        return Objects.isNull(userModel) ? "logout error" : userModel.toString();
    }
}