package website.ylab.financetracker.application.domain.service.auth;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.out.auth.ExternalUserStorage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDataValidatorTest {
    String name = "TestUser";
    String email = "test@test.com";
    UserModel user = createUser();
    UserDAO userDAO = new UserDAO(getStorage(user));


    @Test
    void isUniqueName() {
        UserDataValidator validator = new UserDataValidator(userDAO);
        assertFalse(validator.isUniqueName(name));
        assertTrue(validator.isUniqueName("uniqueUser"));
    }

    @Test
    void isUniqueEmail() {
        UserDataValidator validator = new UserDataValidator(userDAO);
        assertFalse(validator.isUniqueEmail(email));
        assertTrue(validator.isUniqueEmail("unique@email.com"));
    }

    private UserModel createUser() {
        UserModel user = new UserModel();
        user.setUsername(name);
        user.setPassword("testPassword");
        user.setEmail(email);
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