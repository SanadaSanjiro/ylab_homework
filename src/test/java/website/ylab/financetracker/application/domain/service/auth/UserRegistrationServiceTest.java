package website.ylab.financetracker.application.domain.service.auth;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.out.auth.ExternalUserStorage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationServiceTest {
    String name = "TestUser";
    String email = "test@test.com";
    UserModel user = createUser();
    UserModel admin;
    UserDAO userDAO = new UserDAO(getStorage(user));
    UserDataValidator validator = new UserDataValidator(userDAO);
    UserRegistrationService registrationService;

    @Test
    void registerNewUser() {
        UserModel newUser = new UserModel();
        newUser.setUsername(name);
        newUser.setEmail(email);

        registrationService = new UserRegistrationService(userDAO, validator);
        assertNull(registrationService.registerNewUser(newUser));
        newUser.setUsername("UniqueName");
        assertNull(registrationService.registerNewUser(newUser));
        newUser.setUsername(name);
        newUser.setEmail("unique@email.com");
        assertNull(registrationService.registerNewUser(newUser));
        newUser.setUsername("UniqueName");
        assertNotNull(registrationService.registerNewUser(newUser));
    }

    @Test
    void adminCreationTest() {
        admin=null;
        registrationService = new UserRegistrationService(userDAO, validator);
        assertNotNull(admin);
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
                if (user1.getUsername().equals("admin")) {
                    admin=user1;
                    return Optional.of(admin);
                }
                return Optional.of(user);
            }

            @Override
            public Optional<UserModel> getById(long id) {
                return Optional.of(user);
            }

            @Override
            public Optional<UserModel> findByName(String username) {
                if (username.equals("admin")) return Optional.empty();
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