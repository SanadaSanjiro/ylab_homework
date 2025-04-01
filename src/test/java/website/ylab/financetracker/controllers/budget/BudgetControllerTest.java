package website.ylab.financetracker.controllers.budget;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.service.budget.BudgetService;
import website.ylab.financetracker.service.budget.TrackerBudget;

import static org.junit.jupiter.api.Assertions.*;
class BudgetControllerTest {
    BudgetService service = Mockito.mock(BudgetService.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    BudgetController controller = new BudgetController(service);

    @Test
    void getBudget() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(service.getByUserId(Mockito.anyLong())).thenReturn(new BudgetResponse().setLimit(100.0));

        ResponseEntity<BudgetResponse> result = controller.getBudget(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertTrue(100.0 == result.getBody().getLimit());
    }

    @Test
    void setBudget() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(service.setBudget(Mockito.any())).thenReturn(new BudgetResponse().setLimit(100.0));

        ResponseEntity<BudgetResponse> result = controller.setBudget(new TrackerBudget().setLimit(100.0), session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertTrue(100.0 == result.getBody().getLimit());
    }
}