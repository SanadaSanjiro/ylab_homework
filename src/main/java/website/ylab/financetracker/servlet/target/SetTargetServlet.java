package website.ylab.financetracker.servlet.target;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.targets.TargetService;
import website.ylab.financetracker.service.targets.TrackerTarget;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

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
            try (Scanner scanner = new Scanner(req.getInputStream(), "UTF-8")) {
                String jsonData = scanner.useDelimiter("\\A").next();
                TrackerTarget target = objectMapper.readValue(jsonData, TrackerTarget.class);
                target.setUserId(Long.parseLong(useridObj.toString()));
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
    }
}