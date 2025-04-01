package website.ylab.financetracker.service.transactions;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFilterTest {
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    int counter=0;
    TransactionType type1 = TransactionType.INCOME;
    TransactionType type2 = TransactionType.EXPENSE;
    String category1 = "Travel";
    String category2 = "Food";
    TrackerUser user = new TrackerUser();
    TransactionResponse response1 = getResponse(type1, category1);
    TransactionResponse response2 = getResponse(type2, category2);
    List<TransactionResponse> list = List.of(response1, response2);

    @Test
    void dateFilter() {
        Date dateFilter = new Date();
        try {
            dateFilter = formatter.parse("01.01.2025");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(1, TransactionFilter.filter(list, dateFilter).size());
    }

    @Test
    void categoryFilter() {
        String categoryFilter = category1;
        assertEquals(1, TransactionFilter.filter(list, categoryFilter).size());
    }

    @Test
    void typeFilter1() {
        TransactionType typeFilter = type2;
        assertEquals(1, TransactionFilter.filter(list, typeFilter).size());
    }

    private TransactionResponse getResponse(TransactionType type, String category) {
        TransactionResponse response = new TransactionResponse();
        response.setId(counter++)
                .setType(type.toString())
                .setAmount(counter * 100)
                .setCategory(category)
                .setId(user.getId());
        try {
            response.setDate(formatter.parse(counter + ".01.2025"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }
}