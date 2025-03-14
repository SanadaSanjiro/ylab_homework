package website.ylab.financetracker.adapter.in.auth;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.adapter.util.ConstantStorage;

import static org.junit.jupiter.api.Assertions.*;

class UserDataVerifierTest {
    UserDataVerifier verifier = new UserDataVerifier(ConstantStorage.MAIL_REGEXP, ConstantStorage.NAME_REGEXP);

    @Test
    void isValidName() {
        assertTrue(verifier.isValidName("User123"));
        assertFalse(verifier.isValidName("VeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVery" +
                "VeryVeryVeryVeryVeryVeryVeryBigUserName"));
        assertFalse(verifier.isValidName("Some$h!%&"));
    }

    @Test
    void isValidEmail() {
        assertTrue(verifier.isValidEmail("good@eamil.com"));
        assertFalse(verifier.isValidEmail("not a email"));
    }
}