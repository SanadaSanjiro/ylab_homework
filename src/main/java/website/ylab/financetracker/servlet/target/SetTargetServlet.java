package website.ylab.financetracker.servlet.target;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.targets.TargetService;
import website.ylab.financetracker.service.targets.TrackerTarget;

import java.io.IOException;

@WebServlet(name = "setTarget", value ="/target/set")
public class SetTargetServlet extends HttpServlet {
    private final TargetService targetService;
    private final ObjectMapper objectMapper;

    public SetTargetServlet() {
        this.targetService = ServiceProvider.getTargetService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // collect user id parameter
        String id = req.getParameter("id");
        // the string value is parse as integer to id
        long userId = Integer.parseInt(id);
        String targetString = req.getParameter("amount");
        double amount = Double.parseDouble(targetString);
        TrackerTarget target = new TrackerTarget().setUserId(userId).setAmount(amount);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setBufferSize(4096);

        TargetResponse response = targetService.setTarget(target);
        byte[] bytes =  objectMapper.writeValueAsBytes(response);
        resp.getOutputStream().write(bytes);
    }
}