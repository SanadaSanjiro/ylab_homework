package website.ylab.financetracker.in.dto.transaction;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperTest {
    long id = 1L;
    TransactionType type = TransactionType.INCOME;
    double amount = 100.0;
    String category = "Travel";
    Date date = new Date();
    String description = "Test";
    String uuid = "uuid";
    long userId = 1L;

    @Test
    void toResponse() {
        TransactionMapper mapper = TransactionMapper.INSTANCE;
        TrackerTransaction transaction = getTransaction();
        TransactionResponse response = mapper.toResponse(transaction);
        assertEquals(id, response.getId());
        assertEquals(type.toString(), response.getType());
        assertEquals(amount,response.getAmount());
        assertEquals(category, response.getCategory());
        assertEquals(date, response.getDate());
        assertEquals(description,response.getDescription());
        assertEquals(uuid, response.getUuid());
    }

    @Test
    void toTransactionResponseList() {
        TransactionMapper mapper = TransactionMapper.INSTANCE;
        TrackerTransaction transaction = getTransaction();
        List<TrackerTransaction> list = List.of(transaction);
        List<TransactionResponse> responses = mapper.toTransactionResponseList(list);
        assertFalse(responses.isEmpty());
        TransactionResponse response = responses.get(0);
        assertEquals(id, response.getId());
        assertEquals(type.toString(), response.getType());
        assertEquals(amount, response.getAmount());
        assertEquals(category, response.getCategory());
        assertEquals(date, response.getDate());
        assertEquals(description, response.getDescription());
        assertEquals(uuid, response.getUuid());
    }

    private TrackerTransaction getTransaction() {
        TrackerTransaction transaction = new TrackerTransaction();
        transaction.setId(id);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setDate(date);
        transaction.setUserId(userId);
        transaction.setUuid(uuid);
        return transaction;
    }
}