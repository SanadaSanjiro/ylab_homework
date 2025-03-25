package website.ylab.financetracker.in.servlet.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Scanner;

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
        try (Scanner scanner = new Scanner(req.getInputStream(), "UTF-8")) {
            String jsonData = scanner.useDelimiter("\\A").next();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                TrackerUser user = objectMapper.readValue(jsonData, TrackerUser.class);
                System.out.println(user);
                if (isValidUser(user)) {
                    response = userService.addNewUser(user);
                    if (Objects.nonNull(response)) {
                        resp.setStatus(HttpServletResponse.SC_OK);
                        byte[] bytes = objectMapper.writeValueAsBytes(response);
                        resp.getOutputStream().write(bytes);
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isValidUser(TrackerUser user) {
        String username = user.getUsername();
        if (!UserDataVerificator.isValidName(username) || !userService.isUniqueName(username)) {
            return false;
        }
        String email = user.getEmail();
        if (!UserDataVerificator.isValidEmail(email) || !userService.isUniqueEmail(email)) {
            return false;
        }
        String password = user.getPassword();
        return UserDataVerificator.isValidPassword(password);
    }
}