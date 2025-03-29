package website.ylab.financetracker.servlet.login;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@WebServlet(name = "logout", value = "/auth/logout")
public class LogoutServlet  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setBufferSize(4096);
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try {
            HttpSession session = req.getSession();
            session.invalidate();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
