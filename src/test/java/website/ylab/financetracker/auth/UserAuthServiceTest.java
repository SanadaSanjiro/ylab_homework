package website.ylab.financetracker.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthServiceTest {
    TrackerUserRepository trackerUserRepository = Mockito.mock(TrackerUserRepository.class);
    String username = "Bob";
    String badUsername = "Alice";
    String password = "123456";
    String wrongPassword = "1234";
    String email = "bob@gmail.com";
    TrackerUser user = getTestUser();

    @Test
    void login() {
        Mockito.when(trackerUserRepository.getAllUsers()).thenReturn(List.of(user));
        UserAuthService userAuthService = new UserAuthService(trackerUserRepository);
        String result = userAuthService.login(username, password);
        assertEquals("User Bob logged in", result);
        result = userAuthService.login(badUsername, password);
        assertEquals("User not found", result);
        result = userAuthService.login(username, wrongPassword);
        assertEquals("Wrong password", result);
    }

    @Test
    void getCurrentUser() {
        Mockito.when(trackerUserRepository.getAllUsers()).thenReturn(List.of(user));
        UserAuthService userAuthService = new UserAuthService(trackerUserRepository);
        userAuthService.login(username, password);
        assertEquals(user, UserAuthService.getCurrentUser());
    }

    private TrackerUser getTestUser() {
        TrackerUser user = new TrackerUser(username, email, password);
        user.setRole(Role.USER);
        user.setEnabled(true);
        return user;
    }
}