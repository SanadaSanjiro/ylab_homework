package website.ylab.financetracker.service.auth;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.out.repository.UserRepositoryProvider;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryProviderTest {

    @Test
    void getRepository() {
        assertNotNull(UserRepositoryProvider.getRepository());
    }
}