package website.ylab.financetracker.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryProviderTest {

    @Test
    void getRepository() {
        assertNotNull(UserRepositoryProvider.getRepository());
    }
}