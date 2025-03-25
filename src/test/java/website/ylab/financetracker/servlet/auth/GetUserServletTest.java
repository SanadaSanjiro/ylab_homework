package website.ylab.financetracker.servlet.auth;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.in.servlet.auth.GetUserServlet;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.UserService;

class GetUserServletTest {
    long id =1;
    String name = "Bob";

    @Test
    void doGet() {
        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.getResponseById(Mockito.anyLong())).thenReturn(
                new UserResponse().setId(id).setName(name));
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getUserService).thenReturn(userService);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            Mockito.when(req.getParameter("id")).thenReturn("1");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new GetUserServlet().doGet(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("id");
            Mockito.verify(userService, Mockito.times(1))
                    .getResponseById(Mockito.anyLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}