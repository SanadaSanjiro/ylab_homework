package website.ylab.financetracker.servlet.budget;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.service.budget.BudgetService;
import website.ylab.financetracker.service.budget.TrackerBudget;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

//@WebServlet(name = "setBudget", value ="/budget/set")
public class SetBudgetServlet extends HttpServlet {
    private final BudgetService budgetService;
    private final ObjectMapper objectMapper;

    public SetBudgetServlet(BudgetService budgetService) {
        this.budgetService = budgetService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        HttpSession session = req.getSession();
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            try (Scanner scanner = new Scanner(req.getInputStream(), StandardCharsets.UTF_8)) {
                String jsonData = scanner.useDelimiter("\\A").next();
                try {
                    TrackerBudget budget = objectMapper.readValue(jsonData, TrackerBudget.class);
                    budget.setUserId(Long.parseLong(useridObj.toString()));
                    BudgetResponse response = budgetService.setBudget(budget);
                    if (Objects.nonNull(response)) {
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                        byte[] bytes = objectMapper.writeValueAsBytes(response);
                        resp.getOutputStream().write(bytes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}