package website.ylab.financetracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServiceProviderTest {

    @Test
    void getUserAuthService() {
        assertNotNull(ServiceProvider.getUserAuthService());
    }

    @Test
    void getUserService() {
        assertNotNull(ServiceProvider.getUserService());
    }

    @Test
    void getTransactionService() {
        assertNotNull(ServiceProvider.getTransactionService());
    }

    @Test
    void getBudgetService() {
        assertNotNull(ServiceProvider.getBudgetService());
    }
}