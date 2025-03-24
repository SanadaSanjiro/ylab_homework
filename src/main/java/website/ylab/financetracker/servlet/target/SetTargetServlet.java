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
import java.util.Objects;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        try {
            TrackerTarget target = getTarget(req);
            TargetResponse response = targetService.setTarget(target);
            if (Objects.nonNull(response)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                byte[] bytes = objectMapper.writeValueAsBytes(response);
                resp.getOutputStream().write(bytes);
            }
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private TrackerTarget getTarget(HttpServletRequest req) throws NullPointerException, NumberFormatException {
        String id = req.getParameter("id");
        long userId = Long.parseLong(id);
        String targetString = req.getParameter("amount");
        double amount = Double.parseDouble(targetString);
        targetService.deleteByUserId(userId);
        return new TrackerTarget().setUserId(userId).setAmount(amount);
    }
}