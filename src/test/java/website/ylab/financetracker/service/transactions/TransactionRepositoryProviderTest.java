package website.ylab.financetracker.service.transactions;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.out.repository.TransactionRepositoryProvider;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryProviderTest {

    @Test
    void getRepository() {
        assertNotNull(TransactionRepositoryProvider.getRepository());
    }
}