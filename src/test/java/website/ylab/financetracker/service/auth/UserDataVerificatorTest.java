package website.ylab.financetracker.service.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDataVerificatorTest {
    String username = "Bob";
    String invalidName = "@#@$";
    String email = "bob@gmail.com";
    String invalidEmail = "Alice.gmail.com";

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