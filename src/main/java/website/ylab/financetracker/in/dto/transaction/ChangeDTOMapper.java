package website.ylab.financetracker.in.dto.transaction;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.service.transactions.TrackerTransaction;

/**
 * Mapctruct interface to ChangeTransactionDTO mapping
 */
@Mapper
public interface ChangeDTOMapper {
    ChangeDTOMapper INSTANCE = Mappers.getMapper(ChangeDTOMapper.class);

    /**
     * Maps TransactionRequest into TrackerTransaction
     * @param dto TransactionRequest dto to map
     * @return TrackerTransaction
     */
    @Mapping(source = "id", target = "id")
    TrackerTransaction toModel(ChangeTransactionDTO dto);
}
