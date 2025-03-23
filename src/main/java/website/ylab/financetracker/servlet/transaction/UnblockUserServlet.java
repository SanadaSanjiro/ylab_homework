package website.ylab.financetracker.servlet.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.UserService;

import java.io.IOException;

@WebServlet(name = "unblock", value ="/adm/unblock")
public class UnblockUserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UnblockUserServlet() {
        this.userService = ServiceProvider.getUserService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        // the string value is parse as integer to id
        long id = Long.parseLong(userId);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setBufferSize(4096);

        UserResponse response = userService.unblockUser(id);
        byte[] bytes = objectMapper.writeValueAsBytes(response);
        resp.getOutputStream().write(bytes);
    }
}
