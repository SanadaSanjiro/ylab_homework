package website.ylab.financetracker.adapter.in.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.in.auth.ChangeUseCase;

import java.util.Objects;

public class ChangeUserDataAdapter {
    private final ChangeUseCase changeUseCase;
    private final UserInterface userInterface;

    public ChangeUserDataAdapter(ChangeUseCase changeUseCase, UserInterface userInterface) {
        this.changeUseCase = changeUseCase;
        this.userInterface = userInterface;
    }

    public String changeUserData() {
        UserModel user = new UserModel()
                .setUsername(userInterface.getName())
                .setEmail(userInterface.getEmail())
                .setPassword(userInterface.getPassword());
        user = changeUseCase.changeUser(user);
        return Objects.isNull(user) ? "user change error" : user.toString();
    }
}