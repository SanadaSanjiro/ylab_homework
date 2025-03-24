package website.ylab.financetracker.servlet.auth;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.UserService;

class ChangeUserServletTest {
    long id =1;
    String name = "Bob";
    String email = "bob@gmail.com";
    String pass = "password";

    @Test
    void doPost() {
        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.changeUser(Mockito.any())).thenReturn(
                new UserResponse().setId(id).setName(name));
        Mockito.when(userService.isUniqueName(Mockito.any())).thenReturn(true);
        Mockito.when(userService.isUniqueEmail(Mockito.any())).thenReturn(true);
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getUserService).thenReturn(userService);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            Mockito.when(req.getParameter("username")).thenReturn(name);
            Mockito.when(req.getParameter("password")).thenReturn(pass);
            Mockito.when(req.getParameter("email")).thenReturn(email);
            Mockito.when(req.getParameter("id")).thenReturn("1");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new ChangeUserServlet().doPost(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("username");
            Mockito.verify(userService, Mockito.times(1)).changeUser(Mockito.any());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}