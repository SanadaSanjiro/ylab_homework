package website.ylab.financetracker.controllers.stat;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import website.ylab.financetracker.in.dto.stat.BalanceResponse;
import website.ylab.financetracker.in.dto.stat.CategoryExpensesResponse;
import website.ylab.financetracker.in.dto.stat.ReportResponse;
import website.ylab.financetracker.in.dto.stat.TurnoverResponse;
import website.ylab.financetracker.service.stat.StatService;
import website.ylab.financetracker.service.stat.TurnoverRequest;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatControllerTest {
    StatService service = Mockito.mock(StatService.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    StatController controller = new StatController(service);

    @Test
    void getBalance() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(service.getBalance(Mockito.anyLong())).thenReturn(new BalanceResponse().setBalance(100.0));

        ResponseEntity<BalanceResponse> result = controller.getBalance(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(100.0, result.getBody().getBalance());
    }

    @Test
    void getExpenses() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(service.expensesByCategory(Mockito.anyLong())).thenReturn(
                new CategoryExpensesResponse().setExpenses(Map.of("food", 100.0)));

        ResponseEntity<CategoryExpensesResponse> result = controller.getExpenses(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(100.0, result.getBody().getExpenses().get("food"));
    }

    @Test
    void getTurnover() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(service.getTurnover(Mockito.any())).thenReturn(
                new TurnoverResponse().setIncome(100.0).setOutcome(200.0));
        TurnoverRequest request = new TurnoverRequest();
        request.setStartDate(new Date());
        request.setUserid(1L);

        ResponseEntity<TurnoverResponse> result = controller.getTurnover(request, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(100.0, result.getBody().getIncome());
        assertEquals(200.0, result.getBody().getOutcome());
    }

    @Test
    void getReport() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(service.getReport(Mockito.anyLong())).thenReturn(
                new ReportResponse()
                        .setBalance(new BalanceResponse().setBalance(100.0))
                        .setCategory(new CategoryExpensesResponse().setExpenses(Map.of("food", 200.0)))
                        .setTurnover(new TurnoverResponse().setIncome(50).setOutcome(75)));

        ResponseEntity<ReportResponse> result = controller.getReport(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(100.0, result.getBody().getBalance().getBalance());
        assertEquals(200.0, result.getBody().getCategory().getExpenses().get("food"));
        assertEquals(50.0, result.getBody().getTurnover().getIncome());
        assertEquals(75.0, result.getBody().getTurnover().getOutcome());
    }
}