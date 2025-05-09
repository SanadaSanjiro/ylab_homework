package website.ylab.financetracker.in.dto.auth;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.in.mappers.UserMapper;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.TrackerUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    long id = 1L;
    String username = "Bob";
    String password = "123456";
    String email = "bob@gmail.com";
    Role role = Role.USER;

    @Test
    void toResponse() {
        UserMapper mapper = UserMapper.INSTANCE;
        TrackerUser user  = getUser();
        UserResponse response = mapper.toResponse(user);
        assertEquals(id, response.getId());
        assertEquals(username.toLowerCase(), response.getName().toLowerCase());
        assertEquals(email, response.getEmail());
        assertTrue(response.isEnabled());
        assertEquals(role.toString(), response.getRole());
    }


    @Test
    void toUserResponseList() {
        UserMapper mapper = UserMapper.INSTANCE;
        List<TrackerUser> users = List.of(getUser());
        List<UserResponse> responses = mapper.toUserResponseList(users);
        assertFalse(responses.isEmpty());
        UserResponse response = responses.get(0);
        assertEquals(id, response.getId());
        assertEquals(username.toLowerCase(), response.getName().toLowerCase());
        assertEquals(email, response.getEmail());
        assertTrue(response.isEnabled());
        assertEquals(role.toString(), response.getRole());
    }

    private TrackerUser getUser() {
        return new TrackerUser()
                .setUsername(username)
                .setEmail(email)
                .setPassword(password)
                .setEnabled(true)
                .setId(id)
                .setRole(role);
    }
}