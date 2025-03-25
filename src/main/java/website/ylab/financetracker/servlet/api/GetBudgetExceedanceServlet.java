package website.ylab.financetracker.servlet.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.annotations.Loggable;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.api.ApiService;

import java.io.IOException;

@Loggable
@WebServlet(name = "getBudgetExceedance", value ="/api/exceedance")
public class GetBudgetExceedanceServlet extends HttpServlet {
    private final ApiService apiService;
    private final ObjectMapper objectMapper;

    public GetBudgetExceedanceServlet() {
        this.apiService = ServiceProvider.getApiService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        try {
            String userId = req.getParameter("id");
            long id = Long.parseLong(userId);
            boolean response = apiService.isExceeded(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            byte[] bytes = objectMapper.writeValueAsBytes(response);
            resp.getOutputStream().write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}