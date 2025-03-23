package website.ylab.financetracker.servlet.budget;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.budget.BudgetService;
import website.ylab.financetracker.service.budget.TrackerBudget;

import java.io.IOException;

@WebServlet(name = "setBudget", value ="/budget/set")
public class SetBudgetServlet extends HttpServlet {
    private final BudgetService budgetService;
    private final ObjectMapper objectMapper;

    public SetBudgetServlet() {
        this.budgetService = ServiceProvider.getBudgetService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // collect user id parameter
        String id = req.getParameter("id");
        // the string value is parse as integer to id
        long userId = Integer.parseInt(id);
        String limitString = req.getParameter("limit");
        double limit = Double.parseDouble(limitString);
        TrackerBudget budget = new TrackerBudget().setUserId(userId).setLimit(limit);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setBufferSize(4096);

        BudgetResponse response = budgetService.setBudget(budget);
        byte[] bytes =  objectMapper.writeValueAsBytes(response);
        resp.getOutputStream().write(bytes);
    }
}