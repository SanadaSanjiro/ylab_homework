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

import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "addUser", value = "/user/add")
public class AddUserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public AddUserServlet() {
        this.userService = ServiceProvider.getUserService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setBufferSize(4096);
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        UserResponse response = null;
        TrackerUser user = getTrackerUser(req);
        if (Objects.nonNull(user)) {
            response = userService.addNewUser(user);
            if (Objects.nonNull(response)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                byte[] bytes =  objectMapper.writeValueAsBytes(response);
                resp.getOutputStream().write(bytes);
            }
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
        return new TrackerUser().setUsername(username).setEmail(email).setPassword(password);
    }
}