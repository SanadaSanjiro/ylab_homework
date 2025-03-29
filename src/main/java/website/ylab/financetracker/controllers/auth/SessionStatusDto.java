package website.ylab.financetracker.controllers.auth;

import jakarta.servlet.http.HttpSession;
import lombok.Data;

@Data
public class SessionStatusDto {
    private String id;
    private String userid;
    private int maxInactiveInterval;
    private long lastAccessedTime;
    private String username;
    private String role;

    public SessionStatusDto(HttpSession session) {
        this.id = session.getId();
        this.maxInactiveInterval = session.getMaxInactiveInterval();
        this.lastAccessedTime = session.getLastAccessedTime();
        try {
            this.userid = session.getAttribute("userid").toString();
            this.username = session.getAttribute("username").toString();
            this.role = session.getAttribute("role").toString();
        } catch (Exception e) {
            this.userid = "-";
            this.username = "-";
            this.role = "-";
        }
    }
}