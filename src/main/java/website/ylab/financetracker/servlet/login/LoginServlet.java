package website.ylab.financetracker.servlet.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserDataVerificator;
import website.ylab.financetracker.service.auth.UserService;

import java.io.IOException;
import java.util.Scanner;

@WebServlet(name = "login", value = "/auth/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public LoginServlet() {
        this.userService = ServiceProvider.getUserService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setBufferSize(4096);
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        UserResponse response;
        try (Scanner scanner = new Scanner(req.getInputStream(), "UTF-8")) {
            String jsonData = scanner.useDelimiter("\\A").next();
            try {
                TrackerUser user = objectMapper.readValue(jsonData, TrackerUser.class);
                if (isValidInput(user)) {
                    boolean isValid = userService.checkPassword(user);
                    boolean isEnabled = userService.isEnabled(user.getUsername());
                    if (isValid&&isEnabled) {
                        response = userService.getByName(user.getUsername());
                        resp.setStatus(HttpServletResponse.SC_OK);
                        HttpSession session = req.getSession();
                        session.setAttribute("userid", response.getId());
                        session.setAttribute("username", response.getName());
                        session.setAttribute("role", response.getRole());
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidInput(TrackerUser user) {
        String username = user.getUsername();
        if (!UserDataVerificator.isValidName(username)) {
            return false;
        }
        String password = user.getPassword();
        return UserDataVerificator.isValidPassword(password);
    }
}
