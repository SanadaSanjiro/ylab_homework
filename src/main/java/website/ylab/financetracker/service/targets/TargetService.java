package website.ylab.financetracker.service.targets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.in.dto.target.TargetMapper;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.out.repository.TargetRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides methods for changing users targets.
 */
@Service
public class TargetService {
    private final TargetRepository targetRepository;
    private final UserService userService;
    private final TargetMapper mapper = TargetMapper.INSTANCE;
    Logger logger = LogManager.getLogger(TargetService.class);

    @Autowired
    public TargetService(TargetRepository targetRepository, UserService userService) {
        this.targetRepository = targetRepository;
        this.userService = userService;
    }

    /**
     * Sets a target amount for the user.
     * @param request TrackerTarget
     * @return TargetResponse
     */
    public TargetResponse setTarget(TrackerTarget request) {
        logger.info("Get setTarget request");
        Optional<TrackerUser> optional = userService.getById(request.getUserId());
        if (optional.isEmpty()) {
            logger.warn("User not found for a request {}", request);
            return null;
        }
        request.setUuid(UUID.randomUUID().toString());
        Optional<TrackerTarget> targetOptional = targetRepository.setTarget(request);
        return targetOptional.map(mapper::toResponse).orElse(null);
    }

    /**
     * Returns a target by user id
     * @param userId long
     * @return TargetResponse
     */
    public TargetResponse getByUserId(long userId) {
        logger.info("Get getTargetByUserId request");
        Optional<TrackerTarget> optional = targetRepository.getByUserId(userId);
        if (optional.isEmpty()) {
            logger.warn("Target not found for a request {}", userId);
            return null;
        }
        return mapper.toResponse(optional.get());
    }
}
