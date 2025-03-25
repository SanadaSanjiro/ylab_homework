package website.ylab.financetracker.servlet.api;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.api.ApiService;

class GetBudgetExceedanceServletTest {

    @Test
    void doGet() {
        ApiService service = Mockito.mock(ApiService.class);
        Mockito.when(service.isExceeded(Mockito.anyLong())).thenReturn(true);
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getApiService).thenReturn(service);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            Mockito.when(req.getParameter("id")).thenReturn("1");
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new GetBudgetExceedanceServlet().doGet(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("id");
            Mockito.verify(service, Mockito.times(1))
                    .isExceeded(Mockito.anyLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}