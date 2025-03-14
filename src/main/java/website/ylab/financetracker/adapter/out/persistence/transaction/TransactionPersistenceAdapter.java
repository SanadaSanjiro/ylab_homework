package website.ylab.financetracker.adapter.out.persistence.transaction;

import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;
import website.ylab.financetracker.application.port.out.transaction.ExternalTransactionStorage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionPersistenceAdapter implements ExternalTransactionStorage {
    private final TransactionRepository repository;
    private final TransactionEntityMapper mapper;

    public TransactionPersistenceAdapter(TransactionRepository repository, TransactionEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<TransactionModel> create(TransactionModel transaction) {
        TransactionEntity entity= repository.create(mapper.toEntity(transaction));
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TransactionModel> getById(long id) {
        TransactionEntity entity= repository.getById(id);
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TransactionModel> update(TransactionModel transaction) {
        TransactionEntity entity= repository.update(mapper.toEntity(transaction));
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TransactionModel> delete(long id) {
        TransactionEntity entity= repository.delete(id);
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<TransactionModel> getAll() {
        List<TransactionEntity> list= repository.getAll();
        return list.stream().map(mapper::toDomainModel).toList();
    }

    @Override
    public List<TransactionModel> getByUser(long userId) {
        List<TransactionEntity> list= repository.getAll();
        return list.stream().map(mapper::toDomainModel).toList();
    }
}