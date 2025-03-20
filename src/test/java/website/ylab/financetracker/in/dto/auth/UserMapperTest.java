package website.ylab.financetracker.in.dto.auth;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.auth.TrackerUser;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    String username = "Bob";
    String password = "123456";
    String email = "bob@gmail.com";

    @Test
    void toUser() {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        TrackerUser user = mapper.toUser(getRequest());
        assertEquals(username.toLowerCase(), user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
    }

    private UserRequest getRequest() {
        return new UserRequest().setUsername(username).setPassword(password).setEmail(email);
    }
}