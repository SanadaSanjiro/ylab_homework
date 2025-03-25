package website.ylab.financetracker.in.servlet.stat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.in.dto.stat.TurnoverResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.stat.StatService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
        try {
            String userId = req.getParameter("userid");
            long id = Long.parseLong(userId);
            String dateString = req.getParameter("startdate");
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date startDate = formatter.parse(dateString);
            TurnoverResponse response = statService.getTurnover(id, startDate);
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
