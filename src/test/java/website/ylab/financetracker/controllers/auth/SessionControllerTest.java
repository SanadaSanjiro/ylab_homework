package website.ylab.financetracker.controllers.auth;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import website.ylab.financetracker.in.dto.auth.LoginDTO;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserDataVerificator;
import website.ylab.financetracker.service.auth.UserService;

import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {
    String username = "Bob";
    String password = "123456";
    String email = "bob@gmail.com";
    TrackerUser user=new TrackerUser()
            .setUsername(username)
            .setEmail(email)
            .setPassword(password)
            .setId(1L);
    UserResponse response = new UserResponse()
            .setId(1L)
            .setName(username)
            .setEmail(email)
            .setRole(Role.USER.toString())
            .setEnabled(true);
    UserService userService = Mockito.mock(UserService.class);
    UserDataVerificator verifier = Mockito.mock(UserDataVerificator.class);
    SessionController controller = new SessionController(userService, verifier);
    HttpSession session = Mockito.mock(HttpSession.class);

    @Test
    void login() {
        Mockito.when(verifier.isValidName(Mockito.any())).thenReturn(true);
        Mockito.when(verifier.isValidEmail(Mockito.any())).thenReturn(true);
        Mockito.when(verifier.isValidPassword(Mockito.any())).thenReturn(true);

        Mockito.when(userService.checkPassword(Mockito.any())).thenReturn(true);
        Mockito.when(userService.isEnabled(Mockito.any())).thenReturn(true);
        Mockito.when(userService.getByName(Mockito.any())).thenReturn(response);

        LoginDTO dto = new LoginDTO().setUsername(username).setPassword(password);
        ResponseEntity<UserResponse> result = controller.login(dto, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().getName());
    }

    @Test
    void logout() {
        Mockito.when(userService.getResponseById(Mockito.anyLong())).thenReturn(response);
        Mockito.when(session.getAttribute(Mockito.any())).thenReturn("1");
        ResponseEntity<UserResponse> result = controller.logout(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().getName());
    }

    @Test
    void checkSession() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(session.getAttribute("username")).thenReturn(username);
        Mockito.when(session.getAttribute("role")).thenReturn("USER");
        ResponseEntity<SessionStatusDto> result = controller.checkSession(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().getUsername());
    }
}