package website.ylab.financetracker.in.dto.target;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.service.targets.TrackerTarget;

/**
 * Mapctruct interface to target objects mapping
 */

@Mapper()
public interface TargetMapper {
    TargetMapper INSTANCE = Mappers.getMapper(TargetMapper.class);
    /**
     * Maps TrackerTarget into TargetResponse
     * @param target TrackerTarget target
     * @return TargetResponse
     */
    @Mapping(source = "id", target = "id")
    TargetResponse toResponse(TrackerTarget target);

    /**
     * Maps TargetResponse into TrackerTarget
     * @param response TargetResponse response
     * @return TrackerTarget
     */
    @Mapping(source = "id", target = "id")
    TrackerTarget toTarget(TargetResponse response);
}
