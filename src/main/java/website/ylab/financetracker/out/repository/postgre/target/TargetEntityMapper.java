package website.ylab.financetracker.out.repository.postgre.target;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.service.targets.TrackerTarget;

/**
 * Mapctruct interface to target entities mapping
 */
@Mapper
public interface TargetEntityMapper {
    //add instance to call mapper
    TargetEntityMapper INSTANCE = Mappers.getMapper(TargetEntityMapper.class);

    /**
     * Maps TrackerTarget into TargetEntity
     * @param target TrackerTarget target
     * @return TargetEntity
     */
    TargetEntity toEntity(TrackerTarget target);

    /**
     * Maps TargetEntity into TrackerTarget
     * @param entity TargetEntity entity
     * @return TrackerTarget
     */
    TrackerTarget toTarget(TargetEntity entity);
}