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

import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "getTarget", value ="/target/get")
public class GetTargetServlet extends HttpServlet {
    private final TargetService targetService;
    private final ObjectMapper objectMapper;

    public GetTargetServlet() {
        this.targetService = ServiceProvider.getTargetService();
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
            String id = req.getParameter("id");
            long userId = Integer.parseInt(id);
            TargetResponse response = targetService.getByUserId(userId);
            if (Objects.nonNull(response)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                byte[] bytes =  objectMapper.writeValueAsBytes(response);
                resp.getOutputStream().write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}