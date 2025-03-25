package website.ylab.financetracker.in.servlet.budget;

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
import java.util.Objects;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        try {
            TrackerBudget budget = getBudget(req);
            BudgetResponse response = budgetService.setBudget(budget);
            if (Objects.nonNull(response)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                byte[] bytes =  objectMapper.writeValueAsBytes(response);
                resp.getOutputStream().write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TrackerBudget getBudget(HttpServletRequest req) throws NullPointerException, NumberFormatException {
        String id = req.getParameter("id");
        long userId = Long.parseLong(id);
        String limitString = req.getParameter("limit");
        double limit = Double.parseDouble(limitString);
        budgetService.deleteByUserId(userId);
        System.out.println("Budget Servlet. UserID = " + userId);
        return new TrackerBudget().setUserId(userId).setLimit(limit);
    }
}