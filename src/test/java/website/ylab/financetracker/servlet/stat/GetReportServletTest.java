package website.ylab.financetracker.servlet.stat;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.stat.ReportResponse;
import website.ylab.financetracker.in.servlet.stat.GetReportServlet;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.stat.StatService;

class GetReportServletTest {

    @Test
    void doGet() {
        StatService service = Mockito.mock(StatService.class);
        Mockito.when(service.getReport(Mockito.anyLong())).thenReturn(
                new ReportResponse());
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getStatService).thenReturn(service);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            Mockito.when(req.getParameter("userid")).thenReturn("1");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new GetReportServlet().doGet(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("userid");
            Mockito.verify(service, Mockito.times(1))
                    .getReport(Mockito.anyLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}