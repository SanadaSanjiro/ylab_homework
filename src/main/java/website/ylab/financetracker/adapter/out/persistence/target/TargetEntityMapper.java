package website.ylab.financetracker.adapter.out.persistence.target;

import website.ylab.financetracker.application.domain.model.target.TargetModel;

public class TargetEntityMapper {
    TargetEntity toEntity(TargetModel model) {
        TargetEntity entity = new TargetEntity();
        entity.setUserId(model.getUserId());
        entity.setTarget(model.getTargetAmount());
        return entity;
    }

    TargetModel toDomainModel(TargetEntity entity) {
        TargetModel model = new TargetModel();
        model.setUserId(entity.getUserId());
        model.setTargetAmount(entity.getTarget());
        return model;
    }
}