package website.ylab.financetracker.controllers.target;

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
import website.ylab.financetracker.in.dto.target.SetTargetDTO;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.service.targets.TargetService;
import website.ylab.financetracker.service.targets.TrackerTarget;

import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
@RequestMapping("/target")
@Tag(name= "Target functions",
        description = "Allows you to set your target or see its current value")
public class TargetController {
    private final TargetService targetService;
    Logger logger = LogManager.getLogger(TargetController.class);

    @Autowired
    public TargetController(TargetService targetService) {
        this.targetService = targetService;
    }

    @Operation(summary = "Gets users target value.")
    @GetMapping(value="/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TargetResponse> getTarget(HttpSession session) {
        logger.info("Get getTarget request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("GetTarget request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            TargetResponse response = targetService.getByUserId(Long.parseLong(useridObj.toString()));
            if (Objects.nonNull(response)) {
                logger.info("GetTarget request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in getTarget while parsing userId {}", e.getMessage());
        }
        logger.warn("GetTarget request failed {}");
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Sets users target value.")
    @PostMapping(value="/set",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TargetResponse> setTarget(@RequestBody SetTargetDTO dto, HttpSession session) {
        logger.info("Get setTarget request");
        Object useridObj = session.getAttribute("userid");
        if (Objects.isNull(useridObj)) {
            logger.info("SetTarget request rejected: unauthorized");
            return ResponseEntity.status(SC_UNAUTHORIZED).build();
        }
        try {
            TrackerTarget target = new TrackerTarget();
            target.setUserId(Long.parseLong(useridObj.toString()));
            target.setAmount(dto.getAmount());
            TargetResponse response = targetService.setTarget(target);
            if (Objects.nonNull(response)) {
                logger.info("SetTarget request: ok");
                return ResponseEntity.ok(response);
            }
        } catch (NumberFormatException e) {
            logger.warn("Got an exception in setTarget while parsing userId {}", e.getMessage());
        }
        logger.warn("SetTarget request failed {}");
        return ResponseEntity.badRequest().build();
    }
}