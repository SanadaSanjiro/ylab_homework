package website.ylab.financetracker.controllers.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import website.ylab.aspects.Loggable;
import website.ylab.financetracker.in.dto.auth.LoginDTO;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserDataVerificator;
import website.ylab.financetracker.service.auth.UserService;

import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@Tag(name= "Session operations",
        description = "Allows you to log in/out and check session status.")
public class SessionController {
    private final UserService userService;
    private final UserDataVerificator userDataVerificator;
    Logger logger = LogManager.getLogger(SessionController.class);

    @Autowired
    public SessionController(UserService userService, UserDataVerificator userDataVerificator) {
        this.userService = userService;
        this.userDataVerificator = userDataVerificator;
    }

    @Loggable
    @Operation(summary = "Authorizes the user in the system")
    @PostMapping(value ="/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> login(@RequestBody LoginDTO dto, HttpSession session) {
        TrackerUser user = new TrackerUser().setUsername(dto.getUsername()).setPassword(dto.getPassword());
        if (isValidInput(user)) {
            boolean isValid = userService.checkPassword(user);
            boolean isEnabled = userService.isEnabled(user.getUsername());
            if (isValid&&isEnabled) {
                UserResponse response = userService.getByName(user.getUsername());
                session.setAttribute("userid", response.getId());
                session.setAttribute("username", response.getName());
                session.setAttribute("role", response.getRole());
                logger.info("Successfully logged user = {}", user.getUsername());
                return ResponseEntity.ok(response);
            }
        }
        logger.warn("Bad login request for user = {}", user.getUsername());
        return ResponseEntity.badRequest().build();
    }

    @Loggable
    @Operation(summary = "Terminates the user session")
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> logout(HttpSession session) {
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.warn("Trying to log out unauthorized user");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            long userId = Long.parseLong(useridObj.toString());
            UserResponse response = userService.getResponseById(userId);
            if (Objects.nonNull(response)) {
                session.invalidate();
                logger.info("User {} successfully logged off", response);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            logger.warn("Logging off caused exception {}", e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @Loggable
    @Operation(summary = "Allows you to check the session status.")
    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionStatusDto> checkSession(HttpSession session) {
        SessionStatusDto dto = new SessionStatusDto(session);
        logger.info("Session status requested");
        return ResponseEntity.ok(dto);
    }

    private boolean isValidInput(TrackerUser user) {
        String username = user.getUsername();
        if (!userDataVerificator.isValidName(username)) {
            return false;
        }
        String password = user.getPassword();
        return userDataVerificator.isValidPassword(password);
    }
}