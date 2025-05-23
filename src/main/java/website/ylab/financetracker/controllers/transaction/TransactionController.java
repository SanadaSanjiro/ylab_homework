package website.ylab.financetracker.controllers.transaction;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.ylab.aspects.Loggable;
import website.ylab.financetracker.in.mappers.ChangeDTOMapper;
import website.ylab.financetracker.in.dto.transaction.ChangeTransactionDTO;
import website.ylab.financetracker.in.dto.transaction.FilterDTO;
import website.ylab.financetracker.in.mappers.FilterMapper;
import website.ylab.financetracker.in.mappers.RequestMapper;
import website.ylab.financetracker.in.dto.transaction.TransactionRequest;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionService;

import java.util.List;
import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * Transactions processing functions
 */
@RestController
@RequestMapping("/transaction")
@Tag(name= "Transactions processing functions")
public class TransactionController {
    private final TransactionService service;
    Logger logger = LogManager.getLogger(TransactionController.class);
    private final RequestMapper mapper = RequestMapper.INSTANCE;
    private final ChangeDTOMapper changeMapper = ChangeDTOMapper.INSTANCE;
    private final FilterMapper filterMapper = FilterMapper.INSTANCE;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    /**
     * Get transaction by id
     * @param id long
     * @param session HttpSession
     * @return ResponseEntity<TransactionResponse>
     */
    @Loggable
    @Operation(summary = "Get transaction by id")
    @GetMapping(value="/get/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> getById(
            @PathVariable long id, HttpSession session) {
        logger.info("Get getTransaction request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetTransaction request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            long userid =  Long.parseLong(useridObj.toString());
            TransactionResponse  response = service.getById(id);
            if (Objects.nonNull(response) && userid==response.getUserId()) {
                logger.info("GetTransaction request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in getTransaction while parsing userId {}", e.getMessage());
        }
        logger.warn("GetTransaction request failed {}");
        return ResponseEntity.badRequest().build();
    }

    /**
     * Get a filtered list of transactions (by date, category or type)
     * @param dto FilterDTO
     * @param session HttpSession
     * @return ResponseEntity<List<TransactionResponse>>
     */
    @Loggable
    @Operation(summary = "Get a filtered list of transactions (by date, category or type)")
    @PostMapping(value="/filtered",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionResponse>> getFiltered(
            @RequestBody FilterDTO dto, HttpSession session) {
        logger.info("Get getFiltered request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetFiltered request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            TrackerTransaction transaction = filterMapper.toModel(dto);
            transaction.setUserId(Long.parseLong(useridObj.toString()));
            List<TransactionResponse> response = service.getFiltered(transaction);
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

    /**
     * Delete transaction by id
     * @param id long
     * @param session HttpSession
     * @return ResponseEntity<TransactionResponse>
     */
    @Loggable
    @Operation(summary = "Delete transaction by id")
    @DeleteMapping(value = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> deleteTransaction(
            @PathVariable long id, HttpSession session) {
        logger.info("Get deleteTransaction request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("DeleteTransaction request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            TransactionResponse  response = service.getById(id);
            if (Objects.isNull(response) || response.getUserId()!=Long.parseLong(useridObj.toString())) {
                logger.info("DeleteTransaction request rejected: no such transaction or not belong to user");
                return ResponseEntity.status(SC_UNAUTHORIZED).build();
            }
            response = service.deleteTransaction(id);
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

    /**
     * Change transaction
     * @param dto ChangeTransactionDTO
     * @param session HttpSession
     * @return ResponseEntity<TransactionResponse>
     */
    @Loggable
    @Operation(summary = "Change transaction")
    @PutMapping(value="/change",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> changeTransaction(
            @RequestBody ChangeTransactionDTO dto, HttpSession session) {
        logger.info("Get changeTransaction request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("ChangeTransaction request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            TrackerTransaction transaction = changeMapper.toModel(dto);
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

    /**
     * Add new transaction
     * @param dto TransactionRequest
     * @param session HttpSession
     * @return ResponseEntity<TransactionResponse>
     */
    @Loggable
    @Operation(summary = "Add new transaction")
    @PostMapping(value="/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> addTransaction(
            @RequestBody TransactionRequest dto, HttpSession session) {
        logger.info("Get addTransaction request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("AddTransaction request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            TrackerTransaction transaction = mapper.toModel(dto);
            transaction.setUserId(Long.parseLong(useridObj.toString()));
            TransactionResponse response =service.addNewTransaction(transaction);
            if (Objects.nonNull(response)) {
                logger.info("AddTransaction request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Got an exception in addTransaction while parsing userId {}", e.getMessage());
        }
        logger.warn("AddTransaction request failed {}");
        return ResponseEntity.badRequest().build();
    }
}