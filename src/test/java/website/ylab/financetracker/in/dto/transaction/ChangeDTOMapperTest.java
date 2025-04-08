package website.ylab.financetracker.in.dto.transaction;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.service.transactions.TrackerTransaction;

import static org.junit.jupiter.api.Assertions.*;

class ChangeDTOMapperTest {
    double amount = 100.0;
    String category = "Travel";
    String description = "Test";


    @Test
    void toModel() {
        TrackerTransaction transaction = ChangeDTOMapper.INSTANCE.toModel(getDto());
        assertEquals(amount,transaction.getAmount());
        assertEquals(category, transaction.getCategory());
        assertEquals(description,transaction.getDescription());
    }

    private ChangeTransactionDTO getDto() {
        ChangeTransactionDTO transaction = new ChangeTransactionDTO();
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDescription(description);
        return transaction;
    }
}