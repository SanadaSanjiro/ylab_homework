package website.ylab.financetracker.controllers.api;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.ylab.financetracker.in.dto.api.EmailNotification;
import website.ylab.financetracker.service.api.ApiService;

import java.util.List;
import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final ApiService apiService;
    Logger logger = LogManager.getLogger(ApiController.class);

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping(value="/exceedance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getBudgetExceedance(HttpSession session) {
        logger.info("Get GetBudgetExceedance request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetBudgetExceedance request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            long userId = Long.parseLong(useridObj.toString());
            boolean response = apiService.isExceeded(userId);
            logger.info("GetBudgetExceedance request: ok");
            return ResponseEntity.ok(response);
        } catch (NumberFormatException e) {
            logger.warn("Got an exception while parsing userId {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmailNotification>> getEmail(HttpSession session) {
        logger.info("Get GetEmail request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("Get GetEmail request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        List<EmailNotification> response = apiService.getEmailNotifications();
        logger.info("Get GetEmail request: ok");
        return ResponseEntity.ok(response);
    }
}