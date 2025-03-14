package website.ylab.financetracker.application.domain.service.auth;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.out.auth.ExternalUserStorage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class SessionProviderTest {
    UserModel user = createUser();
    UserDAO userDAO = new UserDAO(getStorage(user));
    SessionProvider sessionProvider = new SessionProvider(userDAO);

    @Test
    void login() {
        assertEquals(user, sessionProvider.login(user));
    }

    @Test
    void getCurrentUser() {
        sessionProvider.login(user);
        assertEquals(user, sessionProvider.getCurrentUser());
    }

    @Test
    void logout() {
        sessionProvider.login(user);
        assertNotNull(sessionProvider.logout());
        assertNull(sessionProvider.getCurrentUser());
    }

    private UserModel createUser() {
        UserModel user = new UserModel();
        user.setUsername("TestUser");
        user.setPassword("testPassword");
        return user;
    }

    private ExternalUserStorage getStorage(UserModel user){
        return new ExternalUserStorage() {
            @Override
            public Optional<UserModel> create(UserModel user1) {
                return Optional.of(user1);
            }

            @Override
            public Optional<UserModel> getById(long id) {
                return Optional.of(user);
            }

            @Override
            public Optional<UserModel> findByName(String username) {
                return Optional.of(user);
            }

            @Override
            public Optional<UserModel> update(UserModel newUser) {
                return Optional.of(user);
            }

            @Override
            public Optional<UserModel> delete(long id) {
                return Optional.of(user);
            }

            @Override
            public List<UserModel> getAllUsers() {
                return List.of(user);
            }
        };
    }
}