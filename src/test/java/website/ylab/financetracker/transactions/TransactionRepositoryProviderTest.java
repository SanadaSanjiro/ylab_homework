package website.ylab.financetracker.transactions;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.out.persistence.TransactionRepositoryProvider;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryProviderTest {

    @Test
    void getRepository() {
        assertNotNull(TransactionRepositoryProvider.getRepository());
    }
}