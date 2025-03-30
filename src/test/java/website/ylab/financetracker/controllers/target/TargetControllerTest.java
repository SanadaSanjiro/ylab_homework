package website.ylab.financetracker.controllers.target;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.service.targets.TargetService;
import website.ylab.financetracker.service.targets.TrackerTarget;

import static org.junit.jupiter.api.Assertions.*;

class TargetControllerTest {
    TargetService service = Mockito.mock(TargetService.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    TargetController controller = new TargetController(service);
    @Test
    void getTarget() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(service.getByUserId(Mockito.anyLong())).thenReturn(new TargetResponse().setAmount(100.0));

        ResponseEntity<TargetResponse> result = controller.getTarget(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertTrue(100.0 == result.getBody().getAmount());
    }

    @Test
    void setTarget() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        Mockito.when(service.setTarget(Mockito.any())).thenReturn(new TargetResponse().setAmount(100.0));

        ResponseEntity<TargetResponse> result = controller.setTarget(new TrackerTarget().setAmount(100.0), session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertTrue(100.0 == result.getBody().getAmount());
    }
}