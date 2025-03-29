package website.ylab.financetracker.service.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDataVerificatorTest {
    String username = "Bob";
    String invalidName = "@#@$";
    String email = "bob@gmail.com";
    String invalidEmail = "Alice.gmail.com";
    UserDataVerificator verificator = new UserDataVerificator();

    @Test
    void isValidEmail() {
        assertTrue(verificator.isValidEmail(email));
        assertFalse(verificator.isValidEmail(invalidEmail));
    }

    @Test
    void isValidName() {
        assertTrue(verificator.isValidName(username));
        assertFalse(verificator.isValidName(invalidName));
    }
}