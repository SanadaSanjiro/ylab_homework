package website.ylab.financetracker.controllers.auth;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserDataVerificator;
import website.ylab.financetracker.service.auth.UserService;

import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserDataVerificator userDataVerificator;
    Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, UserDataVerificator userDataVerificator) {
        this.userService = userService;
        this.userDataVerificator = userDataVerificator;
    }

    @PostMapping(value ="/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> addUser(@RequestBody TrackerUser user) {
        logger.info("Get addUser request");
        if (isValidUser(user)) {
            UserResponse response = userService.addNewUser(user);
            if (Objects.nonNull(response)) {
                logger.info("User {} successfully added", response);
                return ResponseEntity.ok(response); }
        }
        logger.warn("AddUser request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/change",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> changeUser(@RequestBody TrackerUser user, HttpSession session) {
        logger.info("Get changeUser request");
        Object usernameObj = session.getAttribute("username");
        if (Objects.isNull(usernameObj)) {
            logger.info("ChangeUser request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build(); }
        if (isValidUser(user)) {
            UserResponse response = userService.getByName(usernameObj.toString());
            user.setId(response.getId());
            response = userService.changeUser(user);
            if (Objects.nonNull(response)) {
                session.setAttribute("username", response.getName());
                logger.info("User {} successfully changed", response);
                return ResponseEntity.ok(response); }
        }
        logger.warn("ChangeUser request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value="/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> deleteUser(HttpSession session) {
        logger.info("Get deleteUser request");
        Object usernameObj = session.getAttribute("username");
        if (Objects.isNull(usernameObj)) {
            logger.info("DeleteUser request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build(); }
        UserResponse response = userService.getByName(usernameObj.toString());;
        if (Objects.nonNull(response)) {
            response = userService.deleteUser(response.getId());
            if (Objects.nonNull(response)) {
                logger.info("User {} successfully deleted", response);
                session.invalidate();
                return ResponseEntity.ok(response);
            }
        }
        logger.warn("DeleteUser request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(HttpSession session) {
        logger.info("Get getUser request");
        Object usernameObj = session.getAttribute("username");
        if (Objects.isNull(usernameObj)) {
            logger.info("GetUser request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build(); }
        UserResponse response = userService.getByName(usernameObj.toString());;
        if (Objects.nonNull(response)) {
            logger.info("Get user request completed successfully. {} returned", response);
            return ResponseEntity.ok(response);
        }
        logger.warn("GetUser request failed {}");
        return ResponseEntity.badRequest().build();
    }

    private boolean isValidUser(TrackerUser user) {
        String username = user.getUsername();
        if (!userDataVerificator.isValidName(username) || !userService.isUniqueName(username)) {
            return false;
        }
        String email = user.getEmail();
        if (!userDataVerificator.isValidEmail(email) || !userService.isUniqueEmail(email)) {
            return false;
        }
        String password = user.getPassword();
        return userDataVerificator.isValidPassword(password);
    }
}