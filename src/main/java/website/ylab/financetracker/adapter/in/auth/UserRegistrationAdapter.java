package website.ylab.financetracker.adapter.in.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.in.auth.UserRegistrationUseCase;

import java.util.Objects;

public class UserRegistrationAdapter {
    private final UserRegistrationUseCase userRegistrar;
    private final UserInterface userInterface;

    public UserRegistrationAdapter(UserRegistrationUseCase userRegistrar, UserInterface userInterface) {
        this.userRegistrar = userRegistrar;
        this.userInterface = userInterface;
    }

    public String createNewUser() {
        UserModel user = new UserModel()
                .setUsername(userInterface.getName())
                .setEmail(userInterface.getEmail())
                .setPassword(userInterface.getPassword());
        user = userRegistrar.registerNewUser(user);
        return Objects.isNull(user) ? "user creation error" : user.toString();
    }
}