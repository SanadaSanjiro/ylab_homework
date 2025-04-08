package website.ylab.financetracker.in.dto.transaction;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.service.transactions.TrackerTransaction;

/**
 * Mapctruct interface to filter request DTO mapping
 */
@Mapper
public interface FilterMapper {
    FilterMapper INSTANCE = Mappers.getMapper(FilterMapper.class);

    /**
     * Maps TransactionRequest into TrackerTransaction
     * @param dto TransactionRequest dto to map
     * @return TrackerTransaction
     */
    @Mapping(source = "date", target = "date", dateFormat = "dd-MM-YYYY)")
    TrackerTransaction toModel(FilterDTO dto);
}