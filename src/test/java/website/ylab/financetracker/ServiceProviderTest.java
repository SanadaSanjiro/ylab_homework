package website.ylab.financetracker;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.service.ServiceProvider;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServiceProviderTest {


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