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
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "getTransactionFiltered", value ="/transaction/filtered")
public class GetTransactionFilteredServlet extends HttpServlet {
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    public GetTransactionFilteredServlet() {
        this.transactionService = ServiceProvider.getTransactionService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        TrackerTransaction transaction = null;
        List<TransactionResponse> response;
        transaction = createTransaction(req);
        if (Objects.nonNull(transaction)) {
            response = transactionService.getFiltered(transaction);
            if (Objects.nonNull(response)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                byte[] bytes = objectMapper.writeValueAsBytes(response);
                resp.getOutputStream().write(bytes);
            }
        }
    }

    private TrackerTransaction createTransaction(HttpServletRequest req) {
        long id;
        try {
            String transactionId = req.getParameter("userid");
            id = Long.parseLong(transactionId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }

        String dateString = req.getParameter("date");
        TrackerTransaction transaction = new TrackerTransaction();
        try {
            String typeString = req.getParameter("type");
            TransactionType type = TransactionType.valueOf(typeString);
            transaction.setType(type);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = formatter.parse(dateString);
            transaction.setDate(date);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            String category = req.getParameter("category");
            transaction.setCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transaction.setUserId(id);
    }
}