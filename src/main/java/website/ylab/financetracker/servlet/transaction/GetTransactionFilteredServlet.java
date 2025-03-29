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
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

//@WebServlet(name = "getTransactionFiltered", value ="/transaction/filtered")
public class GetTransactionFilteredServlet extends HttpServlet {
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    public GetTransactionFilteredServlet(TransactionService transactionService) {
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
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            TrackerTransaction transaction;
            List<TransactionResponse> response;
            try (Scanner scanner = new Scanner(req.getInputStream(), StandardCharsets.UTF_8)) {
                String jsonData = scanner.useDelimiter("\\A").next();
                System.out.println(jsonData);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                objectMapper.setDateFormat(df);
                transaction = objectMapper.readValue(jsonData, TrackerTransaction.class);
                System.out.println(transaction);
                transaction.setUserId(Long.parseLong(useridObj.toString()));
                if (Objects.nonNull(transaction)) {
                    response = transactionService.getFiltered(transaction);
                    if (Objects.nonNull(response)) {
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                        byte[] bytes = objectMapper.writeValueAsBytes(response);
                        resp.getOutputStream().write(bytes);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}