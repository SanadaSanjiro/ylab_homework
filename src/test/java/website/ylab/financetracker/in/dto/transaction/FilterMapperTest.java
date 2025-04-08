package website.ylab.financetracker.in.dto.transaction;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FilterMapperTest {
    TransactionType type = TransactionType.INCOME;
    String category = "Travel";
    Date date = new Date();

    @Test
    void toModel() {
        TrackerTransaction transaction = FilterMapper.INSTANCE.toModel(getFilter());
        assertEquals(type, transaction.getType());
        assertEquals(category, transaction.getCategory());
        assertEquals(date, transaction.getDate());
    }

    private FilterDTO getFilter() {
        FilterDTO transaction = new FilterDTO();
        transaction.setType(type.toString());
        transaction.setCategory(category);
        transaction.setDate(date);
        return transaction;
    }
}