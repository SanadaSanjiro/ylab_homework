package website.ylab.financetracker.in.servlet.adm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.UserService;

import java.util.Objects;

@WebServlet(name = "block", value ="/adm/block")
public class BlockUserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public BlockUserServlet() {
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
            String userId = req.getParameter("id");
            long id = Long.parseLong(userId);
            UserResponse response = userService.blockUser(id);
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