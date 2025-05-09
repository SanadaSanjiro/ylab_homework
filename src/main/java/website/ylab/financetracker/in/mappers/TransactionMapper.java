package website.ylab.financetracker.in.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.transactions.TrackerTransaction;

import java.util.List;

/**
 * Mapctruct interface to transaction objects mapping
 */
@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    /**
     * Maps TrackerTransaction into TransactionResponse
     * @param transaction TrackerTransaction transaction to map
     * @return TransactionResponse
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date", dateFormat = "dd/MM/YYYY)")
    TransactionResponse toResponse(TrackerTransaction transaction);

    /**
     * Maps List of TrackerTransaction objects into List of TransactionResponse objects
     * @param transactions List<TrackerTransaction> transactions
     * @return List<TransactionResponse>
     */
    List<TransactionResponse> toTransactionResponseList(List<TrackerTransaction> transactions);
}
