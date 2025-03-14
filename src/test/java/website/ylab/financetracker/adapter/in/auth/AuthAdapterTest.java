package website.ylab.financetracker.adapter.in.auth;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.in.auth.LoginUseCase;
import website.ylab.financetracker.application.port.in.auth.LogoutUseCase;

import static org.junit.jupiter.api.Assertions.*;

class AuthAdapterTest {
    String name = "user";
    String email = "user@user.com";
    String pass = "password";
    UserModel user = getUser();
    LoginUseCase loginUseCase;
    LogoutUseCase logoutUseCase;
    UserInterface userInterface;

    @Test
    void login() {
        loginUseCase = getLoginCase();
        logoutUseCase = getLogoutCase();
        userInterface = getUserInterface();
        AuthAdapter adapter = new AuthAdapter(loginUseCase, logoutUseCase, userInterface);
        assertEquals("User{id=0, username='user', email='user@user.com', role=null}", adapter.login());
    }

    @Test
    void loginReturnNull() {
        loginUseCase = new LoginUseCase() {
            @Override
            public UserModel login(UserModel userModel) {
                return null;
            }
        };
        logoutUseCase = getLogoutCase();
        userInterface = getUserInterface();
        AuthAdapter adapter = new AuthAdapter(loginUseCase, logoutUseCase, userInterface);
        assertEquals("login error", adapter.login());
    }

    @Test
    void logout() {
        loginUseCase = getLoginCase();
        logoutUseCase = getLogoutCase();
        userInterface = getUserInterface();
        AuthAdapter adapter = new AuthAdapter(loginUseCase, logoutUseCase, userInterface);
        assertEquals("User{id=0, username='user', email='user@user.com', role=null}", adapter.logout());
    }

    @Test
    void logoutReturnNull() {
        loginUseCase = getLoginCase();
        logoutUseCase = new LogoutUseCase() {
            @Override
            public UserModel logout() {
                return null;
            }
        };
        userInterface = getUserInterface();
        AuthAdapter adapter = new AuthAdapter(loginUseCase, logoutUseCase, userInterface);
        assertEquals("logout error", adapter.logout());
    }

    private LoginUseCase getLoginCase() {
        return new LoginUseCase() {
            @Override
            public UserModel login(UserModel userModel) {
                return user;
            }
        };
    }


    private LogoutUseCase getLogoutCase() {
        return new LogoutUseCase() {
            @Override
            public UserModel logout() {
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