package website.ylab.financetracker.adapter.in.auth;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.in.auth.UserRegistrationUseCase;

import static org.junit.jupiter.api.Assertions.*;
class UserRegistrationAdapterTest {
    String name = "user";
    String email = "user@user.com";
    String pass = "password";
    UserModel user = getUser();
    UserRegistrationUseCase useCase;

    @Test
    void createNewUserSuccess() {
        useCase = new UserRegistrationUseCase() {
            @Override
            public UserModel registerNewUser(UserModel user) {
                return user;
            }
        };
        UserRegistrationAdapter adapter = new UserRegistrationAdapter(useCase, getUserInterface());
        assertEquals("User{id=0, username='user', email='user@user.com', role=null}", adapter.createNewUser());
    }

    @Test
    void createNewUserFail() {
        useCase = new UserRegistrationUseCase() {
            @Override
            public UserModel registerNewUser(UserModel user) {
                return null;
            }
        };
        UserRegistrationAdapter adapter = new UserRegistrationAdapter(useCase, getUserInterface());
        assertEquals("user creation error", adapter.createNewUser());
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


    private UserRegistrationUseCase getUseCase() {
        return new UserRegistrationUseCase() {
            @Override
            public UserModel registerNewUser(UserModel user) {
                return user;
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