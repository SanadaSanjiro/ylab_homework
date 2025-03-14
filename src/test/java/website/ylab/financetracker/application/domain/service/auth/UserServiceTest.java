package website.ylab.financetracker.application.domain.service.auth;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.out.auth.ExternalUserStorage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    String name = "TestUser";
    String email = "test@test.com";
    UserModel user = getUser();
    UserDAO userDAO = new UserDAO(getStorage(user));


    @Test
    void createUser() {
        assertTrue(userDAO.createUser(user).isPresent());
    }

    @Test
    void getUserByName() {
        assertTrue(userDAO.getUserByName(name).isPresent());
    }

    @Test
    void changeUser() {
        assertTrue(userDAO.changeUser(user).isPresent());
    }

    @Test
    void deleteUser() {
        assertTrue(userDAO.deleteUser(1L).isPresent());
    }

    @Test
    void getAllUsers() {
        assertFalse(userDAO.getAllUsers().isEmpty());
    }

    @Test
    void getUserById() {
        assertTrue(userDAO.getUserById(1L).isPresent());
    }

    private UserModel getUser() {
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
                return Optional.of(user);
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