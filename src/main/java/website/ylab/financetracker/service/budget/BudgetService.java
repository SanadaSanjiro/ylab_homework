package website.ylab.financetracker.service.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.ylab.financetracker.in.dto.budget.BudgetMapper;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.out.repository.BudgetRepository;
import website.ylab.financetracker.service.auth.UserService;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides methods for changing budget data.
 */
@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserService userService;
    private final BudgetMapper mapper = BudgetMapper.INSTANCE;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository, UserService userService) {
        this.budgetRepository = budgetRepository;
        this.userService = userService;
    }

    /**
     * Sets a budget limit for the user.
     * @param budget TrackerBudget
     * @return BudgetResponse
     */
    public BudgetResponse setBudget(TrackerBudget budget) {
        long userId =budget.getUserId();
        Optional<TrackerUser> optional = userService.getById(userId);
        if (optional.isEmpty()) return null;
        budget.setUuid(UUID.randomUUID().toString());
        Optional<TrackerBudget> optionalBudget = budgetRepository.setBudget(budget);
        return optionalBudget.map(mapper::toResponse).orElse(null);
    }

    /**
     * Returns a budget by its id
     * @param id long
     * @return BudgetResponse
     */
    public BudgetResponse getBudget(long id) {
        Optional<TrackerBudget> optional = budgetRepository.getById(id);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * Returns a budget by user id
     * @param userId long
     * @return BudgetResponse
     */
    public BudgetResponse getByUserId(long userId) {
        Optional<TrackerBudget> optional = budgetRepository.getByUserId(userId);
        return optional.map(mapper::toResponse).orElse(null);
    }
}