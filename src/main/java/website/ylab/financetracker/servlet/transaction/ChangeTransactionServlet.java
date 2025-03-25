package website.ylab.financetracker.servlet.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

@WebServlet(name = "changeTransaction", value ="/transaction/change")
public class ChangeTransactionServlet extends HttpServlet {
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    public ChangeTransactionServlet() {
        this.transactionService = ServiceProvider.getTransactionService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        TrackerTransaction transaction = null;
        TransactionResponse response;

        try (Scanner scanner = new Scanner(req.getInputStream(), "UTF-8")) {
            String jsonData = scanner.useDelimiter("\\A").next();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                transaction = objectMapper.readValue(jsonData, TrackerTransaction.class);
                System.out.println(transaction);
                response = transactionService.changeTransaction(transaction);
                if (Objects.nonNull(response)) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    byte[] bytes = objectMapper.writeValueAsBytes(response);
                    resp.getOutputStream().write(bytes);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}