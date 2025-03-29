package website.ylab.financetracker.servlet.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import website.ylab.financetracker.annotations.Loggable;
import website.ylab.financetracker.in.dto.api.EmailNotification;
import website.ylab.financetracker.service.api.ApiService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Loggable
//@WebServlet(name = "getEmailNotifications", value ="/api/notifications")
public class GetNotificationsServlet extends HttpServlet {
    private final ApiService apiService;
    private final ObjectMapper objectMapper;

    @Autowired
    public GetNotificationsServlet(ApiService apiService) {
        this.apiService = apiService;
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
        String username = session.getAttribute("username").toString();
        if (username.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            List<EmailNotification> response = apiService.getEmailNotifications();
            if (Objects.nonNull(response)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                byte[] bytes = objectMapper.writeValueAsBytes(response);
                resp.getOutputStream().write(bytes);
            }
        }
    }
}
