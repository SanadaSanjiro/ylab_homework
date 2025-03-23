package website.ylab.financetracker.servlet.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;

import java.io.IOException;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        // the string value is parse as integer to id
        long id = Long.parseLong(userId);
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        TrackerUser user = new TrackerUser().setId(id).setUsername(username).setEmail(email).setPassword(password);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setBufferSize(4096);

        UserResponse response = userService.changeUser(user);
        byte[] bytes =  objectMapper.writeValueAsBytes(response);
        resp.getOutputStream().write(bytes);
    }
}
