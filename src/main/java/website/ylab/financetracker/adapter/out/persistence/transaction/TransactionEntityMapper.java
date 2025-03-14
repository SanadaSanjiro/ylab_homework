package website.ylab.financetracker.adapter.out.persistence.transaction;

import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;
import website.ylab.financetracker.transactions.TransactionType;

import java.util.Optional;

public class TransactionEntityMapper {
    TransactionEntity toEntity(TransactionModel model) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(model.getId());
        entity.setAmount(model.getAmount());
        entity.setDate(model.getDate());
        entity.setType(model.getType().toString());
        entity.setDescription(model.getDescription());
        entity.setCategory(model.getCategory());
        entity.setUserId(model.getUserId());
        return entity;
    }

    TransactionModel toDomainModel(TransactionEntity entity) {
        TransactionModel model = new TransactionModel();
        model.setId(entity.getId());
        model.setAmount(model.getAmount());
        model.setDate(entity.getDate());
        Optional<TransactionType> optional = TransactionType.fromString(entity.getType());
        if (optional.isEmpty()) {
            throw new RuntimeException("Data inconsistent! Cannot convert string " + entity.getType()
                    + "into TransactionType");
        }
        model.setType(optional.get());
        model.setDescription(entity.getDescription());
        model.setCategory(entity.getCategory());
        model.setUserId(entity.getUserId());
        return model;
    }
}
