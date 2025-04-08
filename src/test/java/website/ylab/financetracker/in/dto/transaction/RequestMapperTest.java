package website.ylab.financetracker.in.dto.transaction;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RequestMapperTest {
    TransactionType type = TransactionType.INCOME;
    double amount = 100.0;
    String category = "Travel";
    Date date = new Date();
    String description = "Test";

    @Test
    void toModel() {
        TrackerTransaction transaction = RequestMapper.INSTANCE.toModel(getRequest());
        assertEquals(type, transaction.getType());
        assertEquals(amount,transaction.getAmount());
        assertEquals(category, transaction.getCategory());
        assertEquals(date, transaction.getDate());
        assertEquals(description, transaction.getDescription());
    }

    private TransactionRequest getRequest() {
        TransactionRequest transaction = new TransactionRequest();
        transaction.setType(type.toString());
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setDate(date);
        return transaction;
    }
}