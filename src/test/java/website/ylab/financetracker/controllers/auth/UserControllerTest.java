package website.ylab.financetracker.controllers.auth;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserDataVerificator;
import website.ylab.financetracker.service.auth.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    String username = "Bob";
    String newUsername = "Alice";
    String password = "123456";
    String email = "bob@gmail.com";
    TrackerUser user;
    UserResponse response;
    UserService userService = Mockito.mock(UserService.class);
    UserDataVerificator verifier = Mockito.mock(UserDataVerificator.class);
    UserController controller = new UserController(userService, verifier);
    HttpSession session = Mockito.mock(HttpSession.class);

    @BeforeEach
    void setUp() {
        user=new TrackerUser()
                .setUsername(username)
                .setEmail(email)
                .setPassword(password)
                .setId(1L);
        response = new UserResponse()
                .setId(1L)
                .setName(username)
                .setEmail(email)
                .setRole(Role.USER.toString())
                .setEnabled(true);
    }

    @Test
    void addUser() {
        Mockito.when(verifier.isValidName(Mockito.any())).thenReturn(true);
        Mockito.when(verifier.isValidEmail(Mockito.any())).thenReturn(true);
        Mockito.when(verifier.isValidPassword(Mockito.any())).thenReturn(true);

        Mockito.when(userService.addNewUser(Mockito.any())).thenReturn(response);
        Mockito.when(userService.isUniqueName(Mockito.any())).thenReturn(true);
        Mockito.when(userService.isUniqueEmail(Mockito.any())).thenReturn(true);

        ResponseEntity<UserResponse> result = controller.addUser(user);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().getName());
    }

    @Test
    void changeUser() {
        Mockito.when(session.getAttribute("username")).thenReturn(username);

        Mockito.when(verifier.isValidName(Mockito.any())).thenReturn(true);
        Mockito.when(verifier.isValidEmail(Mockito.any())).thenReturn(true);
        Mockito.when(verifier.isValidPassword(Mockito.any())).thenReturn(true);

        Mockito.when(userService.getByName(Mockito.any())).thenReturn(response);
        Mockito.when(userService.changeUser(Mockito.any())).thenReturn(response.setName(newUsername));
        Mockito.when(userService.isUniqueName(Mockito.any())).thenReturn(true);
        Mockito.when(userService.isUniqueEmail(Mockito.any())).thenReturn(true);
        user.setUsername(newUsername);

        ResponseEntity<UserResponse> result = controller.changeUser(user, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(newUsername, result.getBody().getName());
    }

    @Test
    void deleteUser() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(userService.deleteUser(Mockito.anyLong())).thenReturn(response);

        ResponseEntity<UserResponse> result = controller.deleteUser(session);
        Mockito.verify(session, Mockito.times(1)).invalidate();
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().getName());
    }

    @Test
    void getUser() {
        Mockito.when(session.getAttribute("username")).thenReturn(username);
        Mockito.when(userService.getByName(Mockito.any())).thenReturn(response);

        ResponseEntity<UserResponse> result = controller.getUser(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().getName());
    }
}