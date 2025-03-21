package website.ylab.financetracker.in.dto.transaction;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.transactions.TrackerTransaction;

import java.util.List;

/**
 * Mapctruct interface to transaction objects mapping
 */
@Mapper
public interface TransactionMapper {
    //add instance to call mapper
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionResponse toResponse(TrackerTransaction transaction);

    List<TransactionResponse> toTransactionResponseList(List<TrackerTransaction> transactions);
}
