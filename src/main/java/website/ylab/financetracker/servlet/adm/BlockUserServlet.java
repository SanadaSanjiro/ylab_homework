package website.ylab.financetracker.servlet.adm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

//@WebServlet(name = "block", value ="/adm/block")
public class BlockUserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public BlockUserServlet(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setBufferSize(4096);

        HttpSession session = req.getSession();
        Object roleObj = session.getAttribute("role");
        if (Objects.isNull(roleObj) || !roleObj.toString().equalsIgnoreCase(Role.ADMIN.toString())) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            try (Scanner scanner = new Scanner(req.getInputStream(), StandardCharsets.UTF_8)) {
                String jsonData = scanner.useDelimiter("\\A").next();
                TrackerUser user = objectMapper.readValue(jsonData, TrackerUser.class);
                UserResponse response = userService.blockUser(user.getId());
                if (Objects.nonNull(response)) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    byte[] bytes = objectMapper.writeValueAsBytes(response);
                    resp.getOutputStream().write(bytes);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}