package website.ylab.financetracker.transactions;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTypeTest {

    @Test
    void fromString() {
        Optional<TransactionType> optional = TransactionType.fromString("income");
        assertTrue(optional.isPresent());
        assertEquals(TransactionType.INCOME, optional.get());

        optional = TransactionType.fromString("BadInput");
        assertFalse(optional.isPresent());
    }
}