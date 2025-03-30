package website.ylab.financetracker.controllers.api;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import website.ylab.financetracker.in.dto.api.EmailNotification;
import website.ylab.financetracker.service.api.ApiService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiControllerTest {
    ApiService service = Mockito.mock(ApiService.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    ApiController controller = new ApiController(service);

    @Test
    void getBudgetExceedance() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(service.isExceeded(Mockito.anyLong())).thenReturn(true);

        ResponseEntity<Boolean> result = controller.getBudgetExceedance(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertTrue(result.getBody().booleanValue());
    }

    @Test
    void getEmail() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(service.getEmailNotifications()).thenReturn(
                List.of(new EmailNotification("email", "body")));

        ResponseEntity<List<EmailNotification>> result = controller.getEmail(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals("email", result.getBody().get(0).getEmail());
        assertEquals("body", result.getBody().get(0).getBody());
    }
}