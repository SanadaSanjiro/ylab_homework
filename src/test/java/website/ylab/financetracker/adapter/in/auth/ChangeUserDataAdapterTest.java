package website.ylab.financetracker.adapter.in.auth;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.in.auth.ChangeUseCase;

import static org.junit.jupiter.api.Assertions.*;

class ChangeUserDataAdapterTest {
    String name = "user";
    String email = "user@user.com";
    String pass = "password";
    UserModel user = getUser();

    @Test
    void changeUserData() {
        ChangeUseCase changeCase = getChangeUserCase();
        UserInterface ui = getUserInterface();
        ChangeUserDataAdapter adapter = new ChangeUserDataAdapter(changeCase, ui);
        assertEquals("User{id=0, username='user', email='user@user.com', role=null}", adapter.changeUserData());
    }

    @Test
    void changeUserError() {
        ChangeUseCase changeCase = new ChangeUseCase() {
            @Override
            public UserModel changeUser(UserModel model) {
                return null;
            }
        };
        UserInterface ui = getUserInterface();
        ChangeUserDataAdapter adapter = new ChangeUserDataAdapter(changeCase, ui);
        assertEquals("user change error", adapter.changeUserData());
    }

    private ChangeUseCase getChangeUserCase() {
        return new ChangeUseCase() {
            @Override
            public UserModel changeUser(UserModel model) {
                return user;
            }
        };
    }

    private UserInterface getUserInterface() {
        return new UserInterface() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getEmail() {
                return email;
            }

            @Override
            public String getPassword() {
                return pass;
            }

            @Override
            public String getData(String message) {
                return "some message";
            }
        };
    }


    private UserModel getUser() {
        return new UserModel()
                .setUsername(name)
                .setPassword(pass)
                .setEmail(email);
    }
}