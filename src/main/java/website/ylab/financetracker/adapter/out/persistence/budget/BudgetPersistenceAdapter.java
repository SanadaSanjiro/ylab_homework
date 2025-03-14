package website.ylab.financetracker.adapter.out.persistence.budget;

import website.ylab.financetracker.application.domain.model.budget.BudgetModel;
import website.ylab.financetracker.application.port.out.budget.ExternalBudgetStorage;

import java.util.Objects;
import java.util.Optional;

public class BudgetPersistenceAdapter implements ExternalBudgetStorage {
    private final BudgetRepository repository;
    private final BudgetEntityMapper mapper;

    public BudgetPersistenceAdapter(BudgetRepository repository, BudgetEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<BudgetModel> create(BudgetModel budget) {
        BudgetEntity entity= repository.create(mapper.toEntity(budget));
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BudgetModel> getById(long id) {
        BudgetEntity entity= repository.getById(id);
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BudgetModel> delete(long id){
        BudgetEntity entity = repository.delete(id);
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }
}