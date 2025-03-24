package website.ylab.financetracker.servlet.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@WebServlet(name = "addTransaction", value ="/transaction/add")
public class AddTransactionServlet extends HttpServlet {
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    public AddTransactionServlet() {
        this.transactionService = ServiceProvider.getTransactionService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        TrackerTransaction transaction = null;
        TransactionResponse response;
        try {
            transaction = createTransaction(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Objects.nonNull(transaction)) {
            response = transactionService.addNewTransaction(transaction);
            if (Objects.nonNull(response)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                byte[] bytes = objectMapper.writeValueAsBytes(response);
                resp.getOutputStream().write(bytes);
            }
        }
    }

    private TrackerTransaction createTransaction(HttpServletRequest req) throws ParseException
            , IllegalArgumentException {
        String typeString = req.getParameter("type");
        String amountString = req.getParameter("amount");
        String category = req.getParameter("category");
        String dateString = req.getParameter("date");
        String description = req.getParameter("description");
        String userIdString = req.getParameter("userid");
        TransactionType type=TransactionType.valueOf(typeString);
        double amount = Double.parseDouble(amountString);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = formatter.parse(dateString);
        long userId = Long.parseLong(userIdString);
        return new TrackerTransaction().setType(type)
                .setAmount(amount)
                .setCategory(category)
                .setDate(date)
                .setDescription(description)
                .setUserId(userId);
    }
}