package website.ylab.financetracker.servlet.stat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import website.ylab.financetracker.in.dto.stat.TurnoverResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.stat.StatService;
import website.ylab.financetracker.service.stat.TurnoverRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Scanner;

@WebServlet(name = "getturnover", value ="/stat/turnover")
public class GetTurnoverServlet extends HttpServlet {
    private final StatService statService;
    private final ObjectMapper objectMapper;

    public GetTurnoverServlet() {
        this.statService = ServiceProvider.getStatService();
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
            try (Scanner scanner = new Scanner(req.getInputStream(), "UTF-8")) {
                String jsonData = scanner.useDelimiter("\\A").next();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                objectMapper.setDateFormat(df);
                TurnoverRequest request = objectMapper.readValue(jsonData, TurnoverRequest.class);
                request.setUserid(Long.parseLong(useridObj.toString()));
                TurnoverResponse response = statService.getTurnover(request);
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
