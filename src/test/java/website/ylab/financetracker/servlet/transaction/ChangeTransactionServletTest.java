package website.ylab.financetracker.servlet.transaction;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;

class ChangeTransactionServletTest {
    long id = 1L;
    String type = TransactionType.INCOME.toString();
    String amount = "100.0";
    String category = "Travel";
    String date = "24.03.2025";
    String description = "Test";
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    void doPut() {
        TransactionService service = Mockito.mock(TransactionService.class);
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getTransactionService).thenReturn(service);
            Mockito.when(service.changeTransaction(Mockito.any())).thenReturn(getResponse());
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(req.getParameter("id")).thenReturn("1");
            Mockito.when(req.getParameter("amount")).thenReturn(amount);
            Mockito.when(req.getParameter( "category")).thenReturn(category);
            Mockito.when(req.getParameter("description")).thenReturn(description);
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new ChangeTransactionServlet().doPut(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("id");
            Mockito.verify(service, Mockito.times(1)).changeTransaction(Mockito.any());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private TransactionResponse getResponse() throws ParseException {
        TransactionResponse transaction = new TransactionResponse();
        transaction.setId(id);
        transaction.setType(type);
        transaction.setAmount(Double.parseDouble(amount));
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setDate(formatter.parse(date));
        transaction.setCategory(category);
        transaction.setUserId(id);
        return transaction;
    }
}