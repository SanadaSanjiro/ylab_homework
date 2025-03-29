package website.ylab.financetracker.servlet.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.auth.UserService;

import java.util.Objects;

//@WebServlet(name = "deleteUser", value ="/user/delete")
public class DeleteUserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public DeleteUserServlet(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        HttpSession session = req.getSession();
        Object usernameObj = session.getAttribute("username");
        if (Objects.isNull(usernameObj)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            try {
                UserResponse response = userService.getByName(usernameObj.toString());;
                if (Objects.nonNull(response)) {
                    response = userService.deleteUser(response.getId());
                    byte[] bytes = objectMapper.writeValueAsBytes(response);
                    resp.getOutputStream().write(bytes);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    session.invalidate();
                } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    }
            } catch(Exception e) { e.printStackTrace(); }
        }
    }
}