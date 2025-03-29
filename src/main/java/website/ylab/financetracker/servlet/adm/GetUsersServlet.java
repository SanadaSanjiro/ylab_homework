package website.ylab.financetracker.servlet.adm;

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
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Loggable
//@WebServlet(name = "getUsers", value ="/adm/users")
public class GetUsersServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public GetUsersServlet(UserService userService) {
        this.userService = userService;
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
        Object roleObj = session.getAttribute("role");
        if (Objects.isNull(roleObj) || !roleObj.toString().equalsIgnoreCase(Role.ADMIN.toString())) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            List<UserResponse> response = userService.getAllUsersResponse();
            if (Objects.nonNull(response)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                byte[] bytes = objectMapper.writeValueAsBytes(response);
                resp.getOutputStream().write(bytes);
            }
        }
    }
}