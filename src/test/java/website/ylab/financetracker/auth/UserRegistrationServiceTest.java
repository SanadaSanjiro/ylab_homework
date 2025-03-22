package website.ylab.financetracker.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.out.persistence.TrackerUserRepository;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationServiceTest {
    TrackerUserRepository trackerUserRepository = Mockito.mock(TrackerUserRepository.class);

    @Test
    void addNewUserSuccess() {
        String username = "Bob";
        String password = "123456";
        String email = "bob@gmail.com";
        TrackerUser user=new TrackerUser(username, email, password);
        user.setId(1L);
        Optional<TrackerUser> userOptional=Optional.of(user);
        Mockito.when(trackerUserRepository.create(Mockito.any())).thenReturn(userOptional);
        UserRegistrationService userRegistrationService = new UserRegistrationService(trackerUserRepository);
        String result = userRegistrationService.addNewUser(username, email, password);
        assertEquals("User bob created with ID 1", result);
    }

    @Test
    void addNewUserFailure() {
        Optional<TrackerUser> userOptional=Optional.empty();
        Mockito.when(trackerUserRepository.create(Mockito.any())).thenReturn(userOptional);
        UserRegistrationService userRegistrationService = new UserRegistrationService(trackerUserRepository);
        String result = userRegistrationService.addNewUser("User", "user@user.com", "123");
        assertEquals("User creation error for name User", result);
    }
}