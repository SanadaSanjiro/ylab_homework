package website.ylab.financetracker.servlet.target;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.in.servlet.target.GetTargetServlet;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.targets.TargetService;

class GetTargetServletTest {
    double amount = 100.0;
    long id =1;


    @Test
    void doGet() {
        TargetService service = Mockito.mock(TargetService.class);
        Mockito.when(service.getByUserId(Mockito.anyLong())).thenReturn(
                new TargetResponse().setId(id).setAmount(amount).setUserId(id));
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getTargetService).thenReturn(service);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            Mockito.when(req.getParameter("id")).thenReturn("1");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new GetTargetServlet().doGet(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("id");
            Mockito.verify(service, Mockito.times(1))
                    .getByUserId(Mockito.anyLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}