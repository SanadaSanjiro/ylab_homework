package website.ylab.financetracker.service.budget;

import website.ylab.financetracker.in.dto.budget.BudgetMapper;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.out.repository.BudgetRepository;
import website.ylab.financetracker.service.auth.UserService;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides methods for changing budget data.
 */
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserService userService = ServiceProvider.getUserService();
    private final BudgetMapper mapper = BudgetMapper.INSTANCE;


    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public BudgetResponse setBudget(TrackerBudget budget) {
        long userId =budget.getUserId();
        Optional<TrackerUser> optional = userService.getById(userId);
        if (optional.isEmpty()) return null;
        budget.setUuid(UUID.randomUUID().toString());
        Optional<TrackerBudget> optionalBudget = budgetRepository.setBudget(budget);
        return optionalBudget.map(mapper::toResponse).orElse(null);
    }

    public BudgetResponse getBudget(long id) {
        Optional<TrackerBudget> optional = budgetRepository.getById(id);
        return optional.map(mapper::toResponse).orElse(null);
    }

    public BudgetResponse deleteBudget(long id) {
        Optional<TrackerBudget> optional = budgetRepository.deleteBudget(id);
        return optional.map(mapper::toResponse).orElse(null);
    }

    public BudgetResponse deleteByUserId(long userId) {
        Optional<TrackerBudget> optional = budgetRepository.getByUserId(userId);
        if (optional.isEmpty()) return null;
        long id = optional.get().getId();
        optional = budgetRepository.deleteBudget(id);
        return optional.map(mapper::toResponse).orElse(null);
    }

    public BudgetResponse getByUserId(long userId) {
        Optional<TrackerBudget> optional = budgetRepository.getByUserId(userId);
        return optional.map(mapper::toResponse).orElse(null);
    }
}
