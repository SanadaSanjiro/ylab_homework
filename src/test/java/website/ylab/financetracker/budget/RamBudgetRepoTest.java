package website.ylab.financetracker.budget;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.BudgetRepository;
import website.ylab.financetracker.out.persistence.ram.budget.RamBudgetRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RamBudgetRepoTest {
    TrackerUser user = new TrackerUser();
    double limit = 100;
    BudgetRepository budgetRepo = new RamBudgetRepo();

    @Test
    void setBudget() {
        Optional<Double> optional = budgetRepo.setBudget(user, limit);
        assertTrue(optional.isPresent());
        assertEquals(limit, optional.get());
    }

    @Test
    void getBudget() {
        budgetRepo.setBudget(user, limit);
        Optional<Double> optional = budgetRepo.getBudget(user);
        assertTrue(optional.isPresent());
        assertEquals(limit, optional.get());
    }

    @Test
    void deleteBudget() {
        budgetRepo.setBudget(user, limit);
        Optional<Double> optional = budgetRepo.deleteBudget(user);
        assertTrue(optional.isPresent());
        assertEquals(limit, optional.get());
        assertFalse(budgetRepo.getBudget(user).isPresent());
    }
}