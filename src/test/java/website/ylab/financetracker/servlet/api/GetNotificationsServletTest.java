package website.ylab.financetracker.servlet.api;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.api.EmailNotification;
import website.ylab.financetracker.in.servlet.api.GetNotificationsServlet;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.api.ApiService;

import java.util.List;

class GetNotificationsServletTest {

    @Test
    void doGet() {
        ApiService service = Mockito.mock(ApiService.class);
        Mockito.when(service.getEmailNotifications()).thenReturn(List.of(new EmailNotification()));
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getApiService).thenReturn(service);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new GetNotificationsServlet().doGet(req, resp);
            Mockito.verify(service, Mockito.times(1))
                    .getEmailNotifications();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}