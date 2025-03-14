package website.ylab.financetracker.adapter.out.persistence.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.domain.model.auth.Role;

import java.util.Optional;

public class UserEntityMapper {
    UserEntity toEntity(UserModel model) {
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setEnabled(model.isEnabled());
        entity.setEmail(model.getEmail());
        entity.setPassword(model.getPassword());
        entity.setRole(model.getRole().toString());
        return entity;
    }

    UserModel toDomainModel(UserEntity entity) {
        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setEnabled(entity.isEnabled());
        model.setEmail(entity.getEmail());
        model.setPassword(entity.getPassword());
        Optional<Role> optional = Role.fromString(entity.getRole());
        if (optional.isPresent()) {
            model.setRole(optional.get());
        } else {
            throw new IllegalArgumentException("String " + entity.getRole() + " can't be converted to Role object");
        }
        return model;
    }
}