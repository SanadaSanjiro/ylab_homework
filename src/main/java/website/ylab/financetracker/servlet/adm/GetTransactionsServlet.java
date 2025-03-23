package website.ylab.financetracker.servlet.adm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.annotations.Loggable;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.transactions.TransactionService;

import java.io.IOException;
import java.util.List;

@Loggable
@WebServlet(name = "getTransactions", value ="/adm/transactions")
public class GetTransactionsServlet extends HttpServlet {
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    public GetTransactionsServlet() {
        this.transactionService = ServiceProvider.getTransactionService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // collect user id parameter
        String userId = req.getParameter("id");
        // the string value is parse as integer to id
        long id = Long.parseLong(userId);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setBufferSize(4096);

        List<TransactionResponse> response = transactionService.getUserTransaction(id);
        byte[] bytes =  objectMapper.writeValueAsBytes(response);
        resp.getOutputStream().write(bytes);
    }
}
