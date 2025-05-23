package website.ylab.financetracker.out.repository.postgre.budget;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.service.budget.TrackerBudget;

/**
 * Mapctruct interface to budget entities mapping
 */
@Mapper
public interface BudgetEntityMapper {
    //add instance to call mapper
    BudgetEntityMapper INSTANCE = Mappers.getMapper(BudgetEntityMapper.class);

    /**
     * Maps TrackerBudget into BudgetEntity
     * @param budget TrackerBudget budget
     * @return BudgetEntity
     */
    @Mapping(source = "id", target = "id")
    BudgetEntity toEntity(TrackerBudget budget);

    /**
     * Maps BudgetEntity into TrackerBudget
     * @param entity BudgetEntity entity
     * @return TrackerBudget
     */
    @Mapping(source = "id", target = "id")
    TrackerBudget toBudget(BudgetEntity entity);
}
