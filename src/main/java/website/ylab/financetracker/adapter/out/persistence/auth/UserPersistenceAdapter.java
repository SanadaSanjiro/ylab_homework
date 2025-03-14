package website.ylab.financetracker.adapter.out.persistence.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.out.auth.ExternalUserStorage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserPersistenceAdapter implements ExternalUserStorage {
    private final UserRepository repository;
    private final UserEntityMapper mapper;

    public UserPersistenceAdapter(UserRepository repository, UserEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<UserModel> create(UserModel user) {
        UserEntity entity= repository.create(mapper.toEntity(user));
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserModel> getById(long id) {
        UserEntity entity= repository.getById(id);
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserModel> findByName(String username) {
        UserEntity entity= repository.findByName(username);
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserModel> update(UserModel newUser) {
        UserEntity entity= repository.update(mapper.toEntity(newUser));
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserModel> delete(long id) {
        UserEntity entity= repository.delete(id);
        if (Objects.nonNull(entity)) {
            return Optional.of(mapper.toDomainModel(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<UserModel> getAllUsers() {
        List<UserEntity> list= repository.getAllUsers();
        return list.stream().map(mapper::toDomainModel).toList();
    }
}