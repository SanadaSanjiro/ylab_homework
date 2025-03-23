package website.ylab.financetracker.service.targets;

import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.in.dto.target.TargetMapper;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.out.repository.TargetRepository;
import website.ylab.financetracker.service.budget.TrackerBudget;

import java.util.Optional;
import java.util.UUID;

public class TargetService {
    private final TargetRepository targetRepository;
    private final UserService userService = ServiceProvider.getUserService();
    private final TargetMapper mapper = TargetMapper.INSTANCE;

    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    public TargetResponse setTarget(TrackerTarget request) {
        Optional<TrackerUser> optional = userService.getById(request.getUserId());
        if (optional.isEmpty()) return null;
        request.setUuid(UUID.randomUUID().toString());
        Optional<TrackerTarget> targetOptional = targetRepository.setTarget(request);
        return targetOptional.map(mapper::toResponse).orElse(null);
    }

    public TargetResponse getTarget(long id) {
        Optional<TrackerTarget> optional = targetRepository.getById(id);
        return optional.map(mapper::toResponse).orElse(null);
    }

    public TargetResponse deleteTarget(long id) {
        Optional<TrackerTarget> optional = targetRepository.deleteTarget(id);
        return optional.map(mapper::toResponse).orElse(null);
    }

    public TargetResponse deleteByUserId(long userId) {
        Optional<TrackerTarget> optional = targetRepository.getByUserId(userId);
        if (optional.isEmpty()) return null;
        optional = targetRepository.deleteTarget(optional.get().getId());
        return optional.map(mapper::toResponse).orElse(null);
    }

    public TargetResponse getByUserId(long userId) {
        Optional<TrackerTarget> optional = targetRepository.getById(userId);
        return optional.map(mapper::toResponse).orElse(null);
    }
}
