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
import java.util.Objects;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        try {
            String userId = req.getParameter("id");
            long id = Long.parseLong(userId);
            String roleString = req.getParameter("role");
            Optional<Role> optional = Role.fromString(roleString);
            if (optional.isPresent()) {
                Role role = optional.get();
                UserResponse response = userService.changeUserRole(id, role);
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
}