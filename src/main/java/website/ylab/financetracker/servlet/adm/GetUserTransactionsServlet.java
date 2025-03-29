package website.ylab.financetracker.servlet.adm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import website.ylab.financetracker.annotations.Loggable;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.transactions.TransactionService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Loggable
//@WebServlet(name = "getTransactions", value ="/adm/transactions")
public class GetUserTransactionsServlet extends HttpServlet {
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    @Autowired
    public GetUserTransactionsServlet(TransactionService transactionService) {
        this.transactionService = transactionService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        HttpSession session = req.getSession();
        Object roleObj = session.getAttribute("role");
        if (Objects.isNull(roleObj) || !roleObj.toString().equalsIgnoreCase(Role.ADMIN.toString())) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            try (Scanner scanner = new Scanner(req.getInputStream(), StandardCharsets.UTF_8)) {
                String jsonData = scanner.useDelimiter("\\A").next();
                TrackerUser user = objectMapper.readValue(jsonData, TrackerUser.class);
                List<TransactionResponse> response = transactionService.getUserTransaction(user.getId());
                if (Objects.nonNull(response)) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    byte[] bytes = objectMapper.writeValueAsBytes(response);
                    resp.getOutputStream().write(bytes);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}