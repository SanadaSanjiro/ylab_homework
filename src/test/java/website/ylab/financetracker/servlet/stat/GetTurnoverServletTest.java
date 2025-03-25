package website.ylab.financetracker.servlet.stat;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.stat.TurnoverResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.stat.StatService;

class GetTurnoverServletTest {
    String date = "24.03.2025";
    @Test
    void doGet() {
        StatService service = Mockito.mock(StatService.class);
        Mockito.when(service.getTurnover(Mockito.anyLong(), Mockito.any())).thenReturn(
                new TurnoverResponse());
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getStatService).thenReturn(service);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            Mockito.when(req.getParameter("userid")).thenReturn("1");
            Mockito.when(req.getParameter("startdate")).thenReturn(date);
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new GetTurnoverServlet().doGet(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("userid");
            Mockito.verify(service, Mockito.times(1))
                    .getTurnover(Mockito.anyLong(), Mockito.any());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}