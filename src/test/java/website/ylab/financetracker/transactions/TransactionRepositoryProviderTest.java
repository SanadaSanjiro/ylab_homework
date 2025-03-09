package website.ylab.financetracker.transactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryProviderTest {

    @Test
    void getRepository() {
        assertNotNull(TransactionRepositoryProvider.getRepository());
    }
}