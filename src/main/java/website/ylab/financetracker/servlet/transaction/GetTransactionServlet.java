package website.ylab.financetracker.servlet.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionService;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

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
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        TrackerTransaction transaction;
        TransactionResponse response;
        HttpSession session = req.getSession();
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            try (Scanner scanner = new Scanner(req.getInputStream(), "UTF-8")) {
                String jsonData = scanner.useDelimiter("\\A").next();
                transaction = objectMapper.readValue(jsonData, TrackerTransaction.class);
                response =transactionService.getById(transaction.getId());
                if (Objects.nonNull(response)) {
                    if (response.getUserId()==Long.parseLong(useridObj.toString())) {
                        resp.setStatus(HttpServletResponse.SC_OK);
                        byte[] bytes = objectMapper.writeValueAsBytes(response);
                        resp.getOutputStream().write(bytes);
                    } else {
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}