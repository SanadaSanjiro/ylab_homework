package website.ylab.financetracker.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDataVerificatorTest {
    TrackerUserRepository trackerUserRepository = Mockito.mock(TrackerUserRepository.class);
    String username = "Bob";
    String uniqueName = "Alice";
    String invalidName = "@#@$";
    String password = "123456";
    String email = "bob@gmail.com";
    String uniqueEmail = "Alice@gmail.com";
    String invalidEmail = "Alice.gmail.com";
    TrackerUser user=new TrackerUser(username, email, password);

    @Test
    void isUniqueName() {
        Mockito.when(trackerUserRepository.getAllUsers()).thenReturn(List.of(user));
        assertFalse(UserDataVerificator.isUniqueName(trackerUserRepository,username));
        assertTrue(UserDataVerificator.isUniqueName(trackerUserRepository,uniqueName));
    }

    @Test
    void isUniqueEmail() {
        Mockito.when(trackerUserRepository.getAllUsers()).thenReturn(List.of(user));
        assertFalse(UserDataVerificator.isUniqueEmail(trackerUserRepository,email));
        assertTrue(UserDataVerificator.isUniqueEmail(trackerUserRepository,uniqueEmail));
    }

    @Test
    void isValidEmail() {
        assertTrue(UserDataVerificator.isValidEmail(email));
        assertFalse(UserDataVerificator.isValidEmail(invalidEmail));
    }

    @Test
    void isValidName() {
        assertTrue(UserDataVerificator.isValidName(username));
        assertFalse(UserDataVerificator.isValidName(invalidName));
    }
}