package website.ylab.financetracker.adapter.out.persistence.budget;

public interface BudgetRepository {
    BudgetEntity create (BudgetEntity budget);
    BudgetEntity getById(long id);
    BudgetEntity delete(long id);
}
