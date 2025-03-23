package website.ylab.financetracker.service.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.in.cli.auth.UserDataVerificator;
import website.ylab.financetracker.out.repository.TrackerUserRepository;

import static org.junit.jupiter.api.Assertions.*;

class UserDataVerificatorTest {
    TrackerUserRepository trackerUserRepository = Mockito.mock(TrackerUserRepository.class);
    String username = "Bob";
    String invalidName = "@#@$";
    String email = "bob@gmail.com";
    String invalidEmail = "Alice.gmail.com";

    @Test
    void isValidEmail() {
        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.isUniqueEmail(email)).thenReturn(true);
        assertTrue(UserDataVerificator.isValidEmail(email, userService));
        assertFalse(UserDataVerificator.isValidEmail(invalidEmail, userService));
    }

    @Test
    void isValidName() {
        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.isUniqueName(username)).thenReturn(true);
        assertTrue(UserDataVerificator.isValidName(username, userService));
        assertFalse(UserDataVerificator.isValidName(invalidName, userService));
    }
}