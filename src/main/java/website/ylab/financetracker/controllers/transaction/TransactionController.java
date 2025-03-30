package website.ylab.financetracker.controllers.transaction;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionService;

import java.util.List;
import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService service;
    Logger logger = LogManager.getLogger(TransactionController.class);

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping(value="/get",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> getById(
            @RequestBody TrackerTransaction transaction, HttpSession session) {
        logger.info("Get getTransaction request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetTransaction request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            TransactionResponse  response =service.getById(transaction.getId());
            if (Objects.nonNull(response)) {
                logger.info("GetTransaction request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in getTransaction while parsing userId {}", e.getMessage());
        }
        logger.warn("GetTransaction request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value="/filtered",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionResponse>> getFiltered(
            @RequestBody TrackerTransaction transaction, HttpSession session) {
        logger.info("Get getFiltered request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetFiltered request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            transaction.setUserId(Long.parseLong(useridObj.toString()));
            List<TransactionResponse> response =service.getFiltered(transaction);
            if (Objects.nonNull(response)) {
                logger.info("GetFiltered request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in getFiltered while parsing userId {}", e.getMessage());
        }
        logger.warn("GetFiltered request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> deleteTransaction(
            @RequestBody TrackerTransaction transaction, HttpSession session) {
        logger.info("Get deleteTransaction request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("DeleteTransaction request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            TransactionResponse  response = service.getById(transaction.getId());
            if (Objects.isNull(response) || response.getUserId()!=Long.parseLong(useridObj.toString())) {
                logger.info("DeleteTransaction request rejected: no such transaction or not belong to user");
                return ResponseEntity.status(SC_UNAUTHORIZED).build();
            }
            response = service.deleteTransaction(transaction.getId());
            if (Objects.nonNull(response)) {
                logger.info("DeleteTransaction request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in deleteTransaction while parsing userId {}", e.getMessage());
        }
        logger.warn("DeleteTransaction request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(value="/change",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> changeTransaction(
            @RequestBody TrackerTransaction transaction, HttpSession session) {
        logger.info("Get changeTransaction request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("ChangeTransaction request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            transaction.setUserId(Long.parseLong(useridObj.toString()));
            TransactionResponse response =service.changeTransaction(transaction);
            if (Objects.nonNull(response)) {
                logger.info("ChangeTransaction request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in changeTransaction while parsing userId {}", e.getMessage());
        }
        logger.warn("ChangeTransaction request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value="/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> addTransaction(
            @RequestBody TrackerTransaction transaction, HttpSession session) {
        logger.info("Get addTransaction request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("AddTransaction request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            transaction.setUserId(Long.parseLong(useridObj.toString()));
            TransactionResponse response =service.addNewTransaction(transaction);
            if (Objects.nonNull(response)) {
                logger.info("AddTransaction request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in addTransaction while parsing userId {}", e.getMessage());
        }
        logger.warn("AddTransaction request failed {}");
        return ResponseEntity.badRequest().build();
    }
}
