package website.ylab.financetracker.servlet.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserDataVerificator;
import website.ylab.financetracker.service.auth.UserService;

import java.util.Objects;

@WebServlet(name = "changeUser", value = "/user/change")
public class ChangeUserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public ChangeUserServlet() {
        this.userService = ServiceProvider.getUserService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        try {
            TrackerUser user = getTrackerUser(req);
            UserResponse response = null;
            if (Objects.nonNull(user)) {

                response = userService.changeUser(user);
                if (Objects.nonNull(response)) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    byte[] bytes = objectMapper.writeValueAsBytes(response);
                    resp.getOutputStream().write(bytes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TrackerUser getTrackerUser(HttpServletRequest req) {
        String username = req.getParameter("username");
        if (!UserDataVerificator.isValidName(username) || !userService.isUniqueName(username)) {
            return null;
        }
        String email = req.getParameter("email");
        if (!UserDataVerificator.isValidEmail(email) || !userService.isUniqueEmail(email)) {
            return null;
        }
        String password = req.getParameter("password");
        if (!UserDataVerificator.isValidPassword(password)) {
            return null;
        }
        String userId = req.getParameter("id");
        long id = Long.parseLong(userId);
        return new TrackerUser().setUsername(username).setEmail(email).setPassword(password).setId(id);
    }
}