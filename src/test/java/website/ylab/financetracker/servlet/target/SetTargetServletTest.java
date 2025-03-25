package website.ylab.financetracker.servlet.target;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.targets.TargetService;

class SetTargetServletTest {
    double amount = 100.0;
    long id =1;

    @Test
    void doPost() {
        TargetService service = Mockito.mock(TargetService.class);
        Mockito.when(service.setTarget(Mockito.any())).thenReturn(
                new TargetResponse().setId(id).setAmount(amount).setUserId(id));
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getTargetService).thenReturn(service);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            Mockito.when(req.getParameter("id")).thenReturn("1");
            Mockito.when(req.getParameter("amount")).thenReturn("100.0");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new SetTargetServlet().doPost(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("id");
            Mockito.verify(service, Mockito.times(1))
                    .setTarget(Mockito.any());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}