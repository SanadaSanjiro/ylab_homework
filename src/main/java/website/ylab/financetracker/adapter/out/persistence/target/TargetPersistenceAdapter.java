package website.ylab.financetracker.adapter.out.persistence.target;

import website.ylab.financetracker.application.domain.model.target.TargetModel;
import website.ylab.financetracker.application.port.out.target.ExternalTargetStorage;

import java.util.Objects;
import java.util.Optional;

public class TargetPersistenceAdapter implements ExternalTargetStorage {
    private final TargetRepository repository;
    private final TargetEntityMapper mapper;

    public TargetPersistenceAdapter(TargetRepository repository, TargetEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<TargetModel> create(TargetModel target) {
        TargetEntity entity= repository.create(mapper.toEntity(target));
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TargetModel> getById(long id) {
        TargetEntity entity= repository.getById(id);
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TargetModel> delete(long id) {
        TargetEntity entity = repository.delete(id);
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }
}