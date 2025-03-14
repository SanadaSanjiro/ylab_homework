package website.ylab.financetracker.adapter.in.budget;

import website.ylab.financetracker.application.domain.model.budget.BudgetModel;
import website.ylab.financetracker.application.port.in.budget.BudgetUseCase;

public class BudgetAdapter {
    private final BudgetUseCase budgetCase;
    private final BudgetInterface budgetInterface;

    public BudgetAdapter(BudgetUseCase budgetCase, BudgetInterface budgetInterface) {
        this.budgetCase = budgetCase;
        this.budgetInterface = budgetInterface;
    }

    public String create() {
        BudgetModel budget = new BudgetModel();
        budget.setLimit(budgetInterface.getLimit());
        return budgetCase.create(budget).toString();
    }

    public String get() {
        return budgetCase.get().toString();
    }

    public String isExceed() {
        return budgetCase.isExceeded() ? "Бюджет превышен" : "Бюджет не превышен";
    }
}