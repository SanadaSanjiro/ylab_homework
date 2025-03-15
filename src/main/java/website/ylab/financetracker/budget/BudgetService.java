package website.ylab.financetracker.budget;

import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.BudgetRepository;

import java.util.Optional;

/**
 * Provides methods for changing budget data.
 */
public class BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public String setBudget(TrackerUser user, Double budget) {
        Optional<Double> optional = budgetRepository.setBudget(user, budget);
        return optional.map(aDouble -> "Budget set to " + aDouble).orElse("Budget not set");
    }

    public Double getBudget(TrackerUser user) {
        Optional<Double> optional = budgetRepository.getBudget(user);
        return optional.orElse(null);
    }

    public String deleteBudget(TrackerUser user) {
        Optional<Double> optional = budgetRepository.deleteBudget(user);
        return optional.map(aDouble -> "Budget successfully deleted").orElse("Error deleting budget");
    }
}
