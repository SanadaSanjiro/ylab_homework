package website.ylab.financetracker.servlet.budget;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.in.servlet.budget.GetBudgetServlet;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.budget.BudgetService;

class GetBudgetServletTest {
    double limit = 100.0;
    long id =1;

    @Test
    void doGet() {
        BudgetService service = Mockito.mock(BudgetService.class);
        Mockito.when(service.getByUserId(Mockito.anyLong())).thenReturn(
                new BudgetResponse().setId(id).setLimit(limit).setUserId(id));
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getBudgetService).thenReturn(service);
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            Mockito.when(req.getParameter("id")).thenReturn("1");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new GetBudgetServlet().doGet(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("id");
            Mockito.verify(service, Mockito.times(1))
                    .getByUserId(Mockito.anyLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}