package website.ylab.financetracker.transactions;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.auth.TrackerUser;

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
    TrackerTransaction transaction1 = getTransaction(type1, category1);
    TrackerTransaction transaction2 = getTransaction(type2, category2);
    List<TrackerTransaction> list = List.of(transaction1, transaction2);

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

    private TrackerTransaction getTransaction(TransactionType type, String category) {
        TrackerTransaction transaction = new TrackerTransaction();
        transaction.setId(counter++);
        transaction.setType(type);
        transaction.setAmount(counter*100);
        transaction.setCategory(category);
        try {
            transaction.setDate(formatter.parse(counter + ".01.2025"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        transaction.setCategory(category);
        transaction.setUserId(user.getId());
        return transaction;
    }
}