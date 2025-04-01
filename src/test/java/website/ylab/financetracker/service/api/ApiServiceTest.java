package website.ylab.financetracker.service.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.service.budget.BudgetService;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiServiceTest {
    long userId = 2L;
    String username = "bob";
    String password = "123456";

    BudgetService budgetService = Mockito.mock(BudgetService.class);
    TransactionService transactionService = Mockito.mock(TransactionService.class);
    UserService userService = Mockito.mock(UserService.class);

    @Test
    void isExceeded() {
        Mockito.when(budgetService.getByUserId(Mockito.anyLong())).thenReturn(getBudgetResponse());
        Mockito.when(transactionService.getUserTransaction(Mockito.anyLong()))
                .thenReturn(List.of(getTransactionResponse()));
        ApiService apiService = new ApiService(budgetService, transactionService, userService);
        assertTrue(apiService.isExceeded(userId));
    }

    @Test
    void getEmailNotifications() {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(getUser()));
        Mockito.when(budgetService.getBudget(Mockito.anyLong())).thenReturn(getBudgetResponse());
        Mockito.when(budgetService.getByUserId(Mockito.anyLong())).thenReturn(getBudgetResponse());
        Mockito.when(transactionService.getUserTransaction(Mockito.anyLong()))
                .thenReturn(List.of(getTransactionResponse()));
        ApiService apiService = new ApiService(budgetService, transactionService, userService);
        assertFalse(apiService.getEmailNotifications().isEmpty());
    }

    private TrackerUser getUser() {
        return new TrackerUser().setUsername(username).setPassword(password).setId(userId);
    }

    private BudgetResponse getBudgetResponse() {
       return new BudgetResponse()
               .setId(1L)
               .setLimit(100.0)
               .setUuid("uuid")
               .setUserId(2L);
    }

    private TransactionResponse getTransactionResponse() {
        long id = 1L;
        TransactionType type = TransactionType.EXPENSE;
        double amount = 150.0;
        String category = "Travel";
        Date date = new Date();
        String description = "Test";

        TransactionResponse transaction = new TransactionResponse();
        transaction.setId(id);
        transaction.setType(type.toString());
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setDate(date);
        transaction.setCategory(category);
        transaction.setUserId(userId);
        return transaction;
    }
}