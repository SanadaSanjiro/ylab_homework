package website.ylab.financetracker.service.budget;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.out.repository.BudgetRepositoryProvider;

import static org.junit.jupiter.api.Assertions.*;

class BudgetRepositoryProviderTest {

    @Test
    void getRepository() {
        assertNotNull(BudgetRepositoryProvider.getRepository());
    }
}