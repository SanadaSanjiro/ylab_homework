package website.ylab.financetracker.service.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.out.repository.TrackerUserRepository;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    TrackerUserRepository repository = Mockito.mock(TrackerUserRepository.class);
    String username = "Bob";
    String newUsername = "Alice";
    String password = "123456";
    String newPassword = "1234";
    String email = "bob@gmail.com";
    String newEmail = "alice@gmail.com";
    TrackerUser user=new TrackerUser()
            .setUsername(username)
            .setEmail(email)
            .setPassword(password)
            .setId(1L);
    TrackerUser newUser =new TrackerUser()
            .setUsername(newUsername)
            .setEmail(newEmail)
            .setPassword(newPassword)
            .setId(1L);
    @Test
    void changeUserSuccess() {
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(repository.update(Mockito.any())).thenReturn(Optional.of(newUser));
        UserService userService = new UserService(repository);
        UserResponse response = userService.changeUser(newUser);
        assertNotNull(response);
        assertEquals(newUsername, response.getName());
        assertEquals(newEmail, response.getEmail());
    }
}