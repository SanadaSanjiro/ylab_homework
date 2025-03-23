package website.ylab.financetracker.servlet.adm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.UserService;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "changeRole", value ="/adm/role")
public class ChangeRoleServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public ChangeRoleServlet() {
        this.userService = ServiceProvider.getUserService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        // the string value is parse as integer to id
        long id = Long.parseLong(userId);
        String roleString = req.getParameter("role");
        Optional<Role> optional = Role.fromString(roleString);
        if (optional.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Role role = optional.get();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setBufferSize(4096);

            UserResponse response = userService.changeUserRole(id, role);
            byte[] bytes = objectMapper.writeValueAsBytes(response);
            resp.getOutputStream().write(bytes);
        }
    }
}