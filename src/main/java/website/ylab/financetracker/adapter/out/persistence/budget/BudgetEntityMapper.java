package website.ylab.financetracker.adapter.out.persistence.budget;


import website.ylab.financetracker.application.domain.model.budget.BudgetModel;

public class BudgetEntityMapper {
    BudgetEntity toEntity(BudgetModel model) {
        BudgetEntity entity = new BudgetEntity();
        entity.setUserId(model.getUserId());
        entity.setLimit(model.getLimit());
        return entity;
    }

    BudgetModel toDomainModel(BudgetEntity entity) {
        BudgetModel model = new BudgetModel();
        model.setUserId(entity.getUserId());
        model.setLimit(entity.getLimit());
        return model;
    }
}