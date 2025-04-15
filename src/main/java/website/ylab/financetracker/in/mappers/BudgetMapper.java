package website.ylab.financetracker.in.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.service.budget.TrackerBudget;

/**
 * Mapctruct interface to budget objects mapping
 */
@Mapper
public interface BudgetMapper {
    BudgetMapper INSTANCE = Mappers.getMapper(BudgetMapper.class);
    /**
     * Maps TrackerBudget into BudgetResponse
     * @param target TrackerBudget target
     * @return BudgetResponse
     */
    @Mapping(source = "id", target = "id")
    BudgetResponse toResponse(TrackerBudget target);

    /**
     * Maps BudgetResponse into TrackerBudget
     * @param response BudgetResponse response
     * @return TrackerBudget
     */
    TrackerBudget toTarget(BudgetResponse response);
}