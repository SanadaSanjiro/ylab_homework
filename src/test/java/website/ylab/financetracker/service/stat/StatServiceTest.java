package website.ylab.financetracker.service.stat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.stat.BalanceResponse;
import website.ylab.financetracker.in.dto.stat.CategoryExpensesResponse;
import website.ylab.financetracker.in.dto.stat.ReportResponse;
import website.ylab.financetracker.in.dto.stat.TurnoverResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StatServiceTest {
    TransactionService transactionService = Mockito.mock(TransactionService.class);
    StatService statService;
    TransactionResponse transaction1 = new TransactionResponse();
    TransactionResponse transaction2 = new TransactionResponse();
    TransactionResponse transaction3 = new TransactionResponse();
    TransactionResponse transaction4 = new TransactionResponse();
    TrackerUser user = new TrackerUser();
    List<TransactionResponse> list = new ArrayList<>();
    Date date = new Date();


    @BeforeEach
    void setUp() {
        transaction1.setType(TransactionType.INCOME.toString());
        transaction2.setType(TransactionType.INCOME.toString());
        transaction3.setType(TransactionType.EXPENSE.toString());
        transaction4.setType(TransactionType.EXPENSE.toString());
        transaction1.setAmount(10);
        transaction2.setAmount(100);
        transaction3.setAmount(5);
        transaction4.setAmount(50);
        transaction1.setUserId(user.getId());
        transaction2.setUserId(user.getId());
        transaction3.setUserId(user.getId());
        transaction4.setUserId(user.getId());
        transaction1.setDate(date);
        transaction2.setDate(date);
        transaction3.setDate(date);
        transaction4.setDate(date);
        transaction1.setCategory("food");
        transaction2.setCategory("taxi");
        transaction3.setCategory("food");
        transaction4.setCategory("taxi");
        list.add(transaction1);
        list.add(transaction2);
        list.add(transaction3);
        list.add(transaction4);
    }

    @Test
    void getBalance() {
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            Mockito.when(transactionService.getUserTransaction(user.getId())).thenReturn(list);
            mock.when(ServiceProvider::getTransactionService).thenReturn(transactionService);
            statService = new StatService();
            BalanceResponse response = statService.getBalance(user.getId());
            assertEquals(110.0, response.getIncome());
            assertEquals(55.0, response.getOutcome());
            assertEquals(55.0, response.getBalance());
        }
    }

    @Test
    void getTurnover() {
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            Mockito.when(transactionService.getUserTransaction(user.getId())).thenReturn(list);
            mock.when(ServiceProvider::getTransactionService).thenReturn(transactionService);
            statService = new StatService();
            TurnoverRequest request = new TurnoverRequest();
            request.setStartDate(getStartDate());
            request.setUserid(user.getId());
            TurnoverResponse response = statService.getTurnover(request);
            assertEquals(110.0, response.getIncome());
            assertEquals(55.0, response.getOutcome());
        }
    }

    @Test
    void expensesByCategory() {
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            Mockito.when(transactionService.getUserTransaction(user.getId())).thenReturn(list);
            mock.when(ServiceProvider::getTransactionService).thenReturn(transactionService);
            statService = new StatService();
            CategoryExpensesResponse response = statService.expensesByCategory(user.getId());
            Map<String, Double> expenses = response.getExpenses();
            assertEquals(50.0, expenses.get("taxi"));
            assertEquals(5.0, expenses.get("food"));
        }
    }

    @Test
    void getReport() {
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            Mockito.when(transactionService.getUserTransaction(user.getId())).thenReturn(list);
            mock.when(ServiceProvider::getTransactionService).thenReturn(transactionService);
            statService = new StatService();
            ReportResponse response = statService.getReport(user.getId());

            BalanceResponse balance = response.getBalance();
            assertEquals(110.0, balance.getIncome());

            TurnoverResponse turnover = response.getTurnover();
            assertEquals(110.0, turnover.getIncome());

            CategoryExpensesResponse category = response.getCategory();
            assertEquals(50.0, category.getExpenses().get("taxi"));
        }
    }

    private Date getStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1000);
        return calendar.getTime();
    }
}