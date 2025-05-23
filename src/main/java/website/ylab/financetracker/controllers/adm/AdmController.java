package website.ylab.financetracker.controllers.adm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.ylab.audit.annotation.Auditable;
import website.ylab.aspects.Loggable;
import website.ylab.financetracker.in.dto.auth.RoleDTO;
import website.ylab.financetracker.in.dto.auth.UserIdDTO;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.service.transactions.TransactionService;

import java.util.List;
import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * Administrator functions controller. All functionality requires an administrator role
 */
@RestController
@RequestMapping("/adm")
@Tag(name= "Administrator functions",
        description = "Allows you to perform operations on users and their data. Requires ADMIN role.")
public class AdmController {
    private final UserService userService;
    private final TransactionService transactionService;
    Logger logger = LogManager.getLogger(AdmController.class);

    @Autowired
    public AdmController(UserService userService, TransactionService transactionService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    /**
     * Block user by id
     * @param dto UserIdDTO
     * @param session HttpSession
     * @return ResponseEntity<UserResponse>
     */
    @Auditable
    @Loggable
    @Operation(summary = "Block user by id")
    @PutMapping(value ="/block",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> blockUser(@RequestBody UserIdDTO dto, HttpSession session) {
        logger.info("Get blockUser request");
        Object roleObj = session.getAttribute("role");
        if (Objects.isNull(roleObj) || !roleObj.toString().equalsIgnoreCase(Role.ADMIN.toString())) {
            logger.info("BlockUser request rejected: don't have permissions");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        System.out.println(dto.getUserid());
        UserResponse response = userService.blockUser(dto.getUserid());
        if (Objects.nonNull(response)) {
            logger.info("User {} successfully blocked", response);
            return ResponseEntity.ok(response);
        }
        logger.warn("BlockUser request failed {}");
        return ResponseEntity.badRequest().build();
    }

    /**
     * Unblock user by id
     * @param dto UserIdDTO
     * @param session HttpSession
     * @return ResponseEntity<UserResponse>
     */
    @Auditable
    @Loggable
    @Operation(summary = "Unblock user by id")
    @PutMapping(value ="/unblock",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> unblockUser(@RequestBody UserIdDTO dto, HttpSession session) {
        logger.info("Get unblockUser request");
        Object roleObj = session.getAttribute("role");
        if (Objects.isNull(roleObj) || !roleObj.toString().equalsIgnoreCase(Role.ADMIN.toString())) {
            logger.info("UnblockUser request rejected: don't have permissions");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        UserResponse response = userService.unblockUser(dto.getUserid());
        if (Objects.nonNull(response)) {
            logger.info("User {} successfully unblocked", response);
            return ResponseEntity.ok(response);
        }
        logger.warn("UnblockUser request failed {}");
        return ResponseEntity.badRequest().build();
    }

    /**
     * Change user's role
     * @param dto RoleDTO
     * @param session HttpSession
     * @return ResponseEntity<UserResponse>
     */
    @Auditable
    @Loggable
    @Operation(summary = "Change user's role")
    @PutMapping(value ="/role",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> changeRole(@RequestBody RoleDTO dto, HttpSession session) {
        logger.info("Get changeRole request");
        Object roleObj = session.getAttribute("role");
        if (Objects.isNull(roleObj) || !roleObj.toString().equalsIgnoreCase(Role.ADMIN.toString())) {
            logger.info("ChangeRole request rejected: don't have permissions");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            Role role = Role.valueOf(dto.getRole());
            UserResponse response = userService.changeUserRole(dto.getUserid(), role);
            if (Objects.nonNull(response)) {
                logger.info("User {} successfully changed role", response);
                return ResponseEntity.ok(response);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Illegal role input. Got an exception {}", e.getMessage());
        }
        logger.warn("ChangeRole request failed {}");
        return ResponseEntity.badRequest().build();
    }

    /**
     * Delete user and all his data by id
     * @param userid long
     * @param session HttpSession
     * @return ResponseEntity<UserResponse>
     */
    @Auditable
    @Loggable
    @Operation(summary = "Delete user and all his data by id")
    @DeleteMapping(value ="/delete/{userid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> deleteUser(@PathVariable long userid, HttpSession session) {
        logger.info("Get deleteUser request");
        Object roleObj = session.getAttribute("role");
        if (Objects.isNull(roleObj) || !roleObj.toString().equalsIgnoreCase(Role.ADMIN.toString())) {
            logger.info("DeleteUser request rejected: don't have permissions");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        UserResponse response = userService.deleteUser(userid);
        if (Objects.nonNull(response)) {
            logger.info("User {} successfully deleted", response);
            return ResponseEntity.ok(response);
        }
        logger.warn("DeleteUser request failed {}");
        return ResponseEntity.badRequest().build();
    }

    /**
     * Get list of all registered users
     * @param session HttpSession
     * @return ResponseEntity<List<UserResponse>>
     */
    @Auditable
    @Loggable
    @Operation(summary = "Get list of all registered users")
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> getUsers(HttpSession session) {
        logger.info("Get getUsers request");
        Object roleObj = session.getAttribute("role");
        if (Objects.isNull(roleObj) || !roleObj.toString().equalsIgnoreCase(Role.ADMIN.toString())) {
            logger.info("GetUsers request rejected: don't have permissions");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        List<UserResponse> response = userService.getAllUsersResponse();
        if (Objects.nonNull(response)) {
            logger.info("GetUsers successfully retrieved");
            return ResponseEntity.ok(response);
        }
        logger.warn("GetUsers request failed {}");
        return ResponseEntity.badRequest().build();
    }

    /**
     * Get user's transactions by user id
     * @param userid long
     * @param session HttpSession
     * @return ResponseEntity<List<TransactionResponse>>
     */
    @Loggable
    @Operation(summary = "Get user's transactions by user id")
    @GetMapping(value = "/transactions/{userid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionResponse>> getUserTransactions(
            @PathVariable long userid, HttpSession session) {
        logger.info("Get getUserTransactions request");
        Object roleObj = session.getAttribute("role");
        if (Objects.isNull(roleObj) || !roleObj.toString().equalsIgnoreCase(Role.ADMIN.toString())) {
            logger.info("GetUserTransactions request rejected: don't have permissions");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        List<TransactionResponse> response = transactionService.getUserTransaction(userid);
        if (Objects.nonNull(response)) {
            logger.info("GetUserTransactions successfully retrieved");
            return ResponseEntity.ok(response);
        }
        logger.warn("GetUserTransactions request failed {}");
        return ResponseEntity.badRequest().build();
    }
}