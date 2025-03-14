package website.ylab.financetracker.adapter.out.persistence.budget;

import java.util.HashMap;
import java.util.Map;

public class BudgetRepositoryImplementation implements BudgetRepository{
    private final Map<Long, BudgetEntity> storage = new HashMap<>();
    @Override
    public BudgetEntity create(BudgetEntity budget) {
        storage.put(budget.getUserId(), budget);
        return budget;
    }

    @Override
    public BudgetEntity getById(long id) {
        return storage.get(id);
    }

    @Override
    public BudgetEntity delete(long id) {
        BudgetEntity budget = storage.get(id);
        storage.remove(id);
        return budget;
    }
}