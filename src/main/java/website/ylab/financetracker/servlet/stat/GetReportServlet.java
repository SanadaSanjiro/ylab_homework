package website.ylab.financetracker.servlet.stat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import website.ylab.financetracker.in.dto.stat.ReportResponse;
import website.ylab.financetracker.service.stat.StatService;

import java.io.IOException;
import java.util.Objects;

//@WebServlet(name = "getreport", value ="/stat/report")
public class GetReportServlet extends HttpServlet {
    private final StatService statService;
    private final ObjectMapper objectMapper;

    public GetReportServlet(StatService statService) {
        this.statService = statService;
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
            try {
                long id = Long.parseLong(useridObj.toString());
                ReportResponse response = statService.getReport(id);
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