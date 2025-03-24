package website.ylab.financetracker.servlet.adm;

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
import java.util.List;

class GetTransactionsServletTest {
    long id =1;
    String type = TransactionType.INCOME.toString();
    String amount = "100.0";
    String category = "Travel";
    String date = "24.03.2025";
    String description = "Test";
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    void doGet() {
        TransactionService service = Mockito.mock(TransactionService.class);
        try (MockedStatic<ServiceProvider> mock = Mockito.mockStatic(ServiceProvider.class)) {
            mock.when(ServiceProvider::getTransactionService).thenReturn(service);
            Mockito.when(service.getUserTransaction(Mockito.anyLong())).thenReturn(getResponse());
            HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);
            Mockito.when(req.getParameter("id")).thenReturn("1");
            Mockito.when(resp.getOutputStream()).thenReturn(out);
            new GetUserTransactionsServlet().doGet(req, resp);
            Mockito.verify(req, Mockito.atLeast(1)).getParameter("id");
            Mockito.verify(service, Mockito.times(1)).getUserTransaction(Mockito.anyLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<TransactionResponse> getResponse() throws ParseException {
        TransactionResponse transaction = new TransactionResponse();
        transaction.setId(id);
        transaction.setType(type);
        transaction.setAmount(Double.parseDouble(amount));
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setDate(formatter.parse(date));
        transaction.setCategory(category);
        transaction.setUserId(id);
        return List.of(transaction);
    }
}