package website.ylab.financetracker.auth;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    TrackerUserRepository trackerUserRepository = Mockito.mock(TrackerUserRepository.class);
    UserAuthService userAuthService = Mockito.mock(UserAuthService.class);
    String username = "Bob";
    String newUsername = "Alice";
    String password = "123456";
    String newPassword = "1234";
    String email = "bob@gmail.com";
    String newEmail = "alice@gmail.com";
    TrackerUser user=new TrackerUser(username, email, password);
    TrackerUser newUser =new TrackerUser(newUsername, newEmail, newPassword);
    @Test
    void changeUserSuccess() {
        Mockito.when(trackerUserRepository.getAllUsers()).thenReturn(List.of(user));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            UserService userService = new UserService(trackerUserRepository);
            assertEquals("User data successfully changed", userService.changeUser(newUser));
        }
    }

    @Test
    void changeUserFailNameInUse() {
        TrackerUser user2 =new TrackerUser(username, newEmail, password);
        Mockito.when(trackerUserRepository.getAllUsers()).thenReturn(List.of(user, user2));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            UserService userService = new UserService(trackerUserRepository);
            assertEquals("Email is already in use", userService.changeUser(newUser));
        }
    }

    @Test
    void changeUserFailEmailInUse() {
        TrackerUser user2 =new TrackerUser(newUsername, newEmail, password);
        Mockito.when(trackerUserRepository.getAllUsers()).thenReturn(List.of(user, user2));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            UserService userService = new UserService(trackerUserRepository);
            assertEquals("Username is already in use", userService.changeUser(newUser));
        }
    }

    @Test
    void deleteCurrentUser() {
        Mockito.when(trackerUserRepository.delete(Mockito.any())).thenReturn(Optional.of(user));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            UserService userService = new UserService(trackerUserRepository);
            assertEquals("User successfully deleted", userService.deleteCurrentUser());
        }

        Mockito.when(trackerUserRepository.delete(Mockito.any())).thenReturn(Optional.empty());
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            UserService userService = new UserService(trackerUserRepository);
            assertEquals("Error deleting user", userService.deleteCurrentUser());
        }
    }
}