package website.ylab.financetracker.servlet.stat;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.stat.BalanceResponse;
import website.ylab.financetracker.in.servlet.stat.GetBalanceServlet;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.stat.StatService;

class GetBalanceServletTest {
    @Test
    void doGet() {
        StatService service = Mockito.mock(StatService.class);
        Mockito.when(service.getBalance(Mockito.anyLong())).thenReturn(
                new BalanceResponse());
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getStatService).thenReturn(service);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            Mockito.when(req.getParameter("userid")).thenReturn("1");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new GetBalanceServlet().doGet(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("userid");
            Mockito.verify(service, Mockito.times(1))
                    .getBalance(Mockito.anyLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}