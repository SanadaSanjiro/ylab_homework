package website.ylab.financetracker.controllers.budget;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.ylab.aspects.Loggable;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.in.dto.budget.SetBudgetDTO;
import website.ylab.financetracker.service.budget.BudgetService;
import website.ylab.financetracker.service.budget.TrackerBudget;

import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
@RequestMapping("/budget")
@Tag(name= "Budget functions",
        description = "Allows you to set your budget limit or see its current value")
public class BudgetController {
    private final BudgetService budgetService;
    Logger logger = LogManager.getLogger(BudgetController.class);

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Loggable
    @Operation(summary = "Gets users budget limit.")
    @GetMapping(value="/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BudgetResponse> getBudget(HttpSession session) {
        logger.info("Get getBudget request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetBudget request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            BudgetResponse response = budgetService.getByUserId(Long.parseLong(useridObj.toString()));
            if (Objects.nonNull(response)) {
                logger.info("GetBudget request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in getBudget while parsing userId {}", e.getMessage());
        }
        logger.warn("GetBudget request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @Loggable
    @Operation(summary = "Sets users budget limit.")
    @PostMapping(value="/set",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BudgetResponse> setBudget(@RequestBody SetBudgetDTO dto, HttpSession session) {
        logger.info("Get setBudget request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("SetBudget request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            TrackerBudget budget = new TrackerBudget();
            budget.setUserId(Long.parseLong(useridObj.toString()));
            budget.setLimit(dto.getLimit());
            BudgetResponse response = budgetService.setBudget(budget);
            if (Objects.nonNull(response)) {
                logger.info("SetBudget request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in setBudget while parsing userId {}", e.getMessage());
        }
        logger.warn("SetBudget request failed {}");
        return ResponseEntity.badRequest().build();
    }
}