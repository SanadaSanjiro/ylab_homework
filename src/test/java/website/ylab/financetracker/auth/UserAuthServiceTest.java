package website.ylab.financetracker.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.out.persistence.TrackerUserRepository;

import java.util.Optional;

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
        Mockito.when(trackerUserRepository.getByName(Mockito.any())).thenReturn(Optional.of(user));
        UserAuthService userAuthService = new UserAuthService(trackerUserRepository);
        String result = userAuthService.login(new TrackerUser().setUsername(username).setPassword(password)).toString();
        System.out.println(result);
        assertEquals("User Bob logged in", result);
        Mockito.when(trackerUserRepository.getByName(Mockito.any())).thenReturn(Optional.empty());
        result = userAuthService.login(new TrackerUser().setUsername(badUsername).setPassword(password)).toString();
        assertEquals("User not found", result);
        Mockito.when(trackerUserRepository.getByName(Mockito.any())).thenReturn(Optional.of(user));
        result = userAuthService.login(new TrackerUser().setUsername(username).setPassword(wrongPassword)).toString();
        assertEquals("Wrong password", result);
    }

    @Test
    void getCurrentUser() {
        Mockito.when(trackerUserRepository.getByName(Mockito.any())).thenReturn(Optional.of(user));
        UserAuthService userAuthService = new UserAuthService(trackerUserRepository);
        userAuthService.login(new TrackerUser().setUsername(username).setPassword(password));
        System.out.println(UserAuthService.getCurrentUser());
        assertEquals(user, UserAuthService.getCurrentUser());
    }

    private TrackerUser getTestUser() {
        TrackerUser user = new TrackerUser(username, email, password);
        user.setRole(Role.USER);
        user.setEnabled(true);
        return user;
    }
}