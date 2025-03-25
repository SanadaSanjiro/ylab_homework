package website.ylab.financetracker.servlet.adm;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.in.servlet.adm.ChangeRoleServlet;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.UserService;

class ChangeRoleServletTest {
    long id =1;

    @Test
    void doPut() {
        UserService service = Mockito.mock(UserService.class);
        Mockito.when(service.changeUserRole(Mockito.anyLong(), Mockito.any())).thenReturn(
                new UserResponse().setId(id));
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getUserService).thenReturn(service);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            Mockito.when(req.getParameter("id")).thenReturn("1");
            Mockito.when(req.getParameter("role")).thenReturn("USER");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new ChangeRoleServlet().doPut(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("id");
            Mockito.verify(service, Mockito.times(1))
                    .changeUserRole(Mockito.anyLong(), Mockito.any());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}