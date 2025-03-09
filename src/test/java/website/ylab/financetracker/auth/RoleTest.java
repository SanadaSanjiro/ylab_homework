package website.ylab.financetracker.auth;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void fromString() {
        Optional<Role> optional = Role.fromString("admin");
        assertTrue(optional.isPresent());
        assertEquals(Role.ADMIN, optional.get());

        optional = Role.fromString("BadInput");
        assertFalse(optional.isPresent());
    }
}