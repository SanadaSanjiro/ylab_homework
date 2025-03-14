package website.ylab.financetracker.adapter.in.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UserCLITest {
    Scanner scanner = Mockito.mock(Scanner.class);
    UserVerifierInterface verifier = getVerifier();
    UserCLI cli = new UserCLI(scanner, verifier);
    String string = "someInput";

    @Test
    void getName() {
        Mockito.when(scanner.nextLine()).thenReturn(string);
        assertEquals(string, cli.getName());
    }

    @Test
    void getEmail() {
        Mockito.when(scanner.nextLine()).thenReturn(string);
        assertEquals(string, cli.getEmail());
    }

    @Test
    void getPassword() {
        Mockito.when(scanner.nextLine()).thenReturn("someInput");
        assertEquals(string, cli.getPassword());
    }

    @Test
    void getData() {
        Mockito.when(scanner.nextLine()).thenReturn("someInput");
        assertEquals(string, cli.getData("Message"));
    }

    private UserVerifierInterface getVerifier() {
        return new UserVerifierInterface() {
            @Override
            public boolean isValidName(String name) {
                return true;
            }

            @Override
            public boolean isValidEmail(String email) {
                return true;
            }
        };
    }

}