package website.ylab.financetracker.servlet.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.transactions.TransactionService;

import java.io.IOException;

@WebServlet(name = "getTransaction", value ="/transaction/get")
public class GetTransactionServlet extends HttpServlet {
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    public GetTransactionServlet() {
        this.transactionService = ServiceProvider.getTransactionService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // collect transaction id parameter
        String transactionId = req.getParameter("id");
        // the string value is parse as integer to id
        long id = Long.parseLong(transactionId);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setBufferSize(4096);

        TransactionResponse response = transactionService.getById(id);
        byte[] bytes =  objectMapper.writeValueAsBytes(response);
        resp.getOutputStream().write(bytes);
    }
}
