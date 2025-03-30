package website.ylab.financetracker.controllers.stat;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.ylab.financetracker.in.dto.stat.BalanceResponse;
import website.ylab.financetracker.in.dto.stat.CategoryExpensesResponse;
import website.ylab.financetracker.in.dto.stat.ReportResponse;
import website.ylab.financetracker.in.dto.stat.TurnoverResponse;
import website.ylab.financetracker.service.stat.StatService;
import website.ylab.financetracker.service.stat.TurnoverRequest;

import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
@RequestMapping("/stat")
public class StatController {
    private final StatService statService;
    Logger logger = LogManager.getLogger(StatController.class);

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping(value="/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceResponse> getBalance(HttpSession session) {
        logger.info("Get getBalance request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetBalance request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            BalanceResponse response = statService.getBalance(Long.parseLong(useridObj.toString()));
            if (Objects.nonNull(response)) {
                logger.info("GetBalance request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in getBalance while parsing userId {}", e.getMessage());
        }
        logger.warn("GetBalance request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value="/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryExpensesResponse> getExpenses(HttpSession session) {
        logger.info("Get getExpenses request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetExpenses request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            CategoryExpensesResponse response = statService.expensesByCategory(Long.parseLong(useridObj.toString()));
            if (Objects.nonNull(response)) {
                logger.info("GetExpenses request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in getExpenses while parsing userId {}", e.getMessage());
        }
        logger.warn("GetExpenses request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value="/turnover",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TurnoverResponse> getTurnover(@RequestBody TurnoverRequest request, HttpSession session) {
        logger.info("Get getTurnover request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetTurnover request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            request.setUserid(Long.parseLong(useridObj.toString()));
            TurnoverResponse response = statService.getTurnover(request);
            if (Objects.nonNull(response)) {
                logger.info("GetTurnover request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in getTurnover while parsing userId {}", e.getMessage());
        }
        logger.warn("GetTurnover request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value="/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportResponse> getReport(HttpSession session) {
        logger.info("Get getReport request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetReport request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            ReportResponse response = statService.getReport(Long.parseLong(useridObj.toString()));
            if (Objects.nonNull(response)) {
                logger.info("GetReport request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in getReport while parsing userId {}", e.getMessage());
        }
        logger.warn("GetReport request failed {}");
        return ResponseEntity.badRequest().build();
    }
}