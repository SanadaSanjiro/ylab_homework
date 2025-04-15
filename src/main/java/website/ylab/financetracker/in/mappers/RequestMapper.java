package website.ylab.financetracker.in.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.in.dto.transaction.TransactionRequest;
import website.ylab.financetracker.service.transactions.TrackerTransaction;

/**
 * Mapctruct interface to transaction request DTO mapping
 */
@Mapper
public interface RequestMapper {
    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    /**
     * Maps TransactionRequest into TrackerTransaction
     * @param dto TransactionRequest dto to map
     * @return TrackerTransaction
     */
    @Mapping(source = "date", target = "date", dateFormat = "dd/MM/YYYY)")
    TrackerTransaction toModel(TransactionRequest dto);
}
