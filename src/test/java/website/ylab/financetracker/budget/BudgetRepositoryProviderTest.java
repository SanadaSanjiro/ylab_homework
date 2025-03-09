package website.ylab.financetracker.budget;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudgetRepositoryProviderTest {

    @Test
    void getRepository() {
        assertNotNull(BudgetRepositoryProvider.getRepository());
    }
}