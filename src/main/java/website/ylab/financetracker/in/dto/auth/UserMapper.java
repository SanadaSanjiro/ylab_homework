package website.ylab.financetracker.in.dto.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.service.auth.TrackerUser;

import java.util.List;

/**
 * Mapctruct interface to user objects mapping
 */

@Mapper()
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Maps TrackerUser into UserResponse
     * @param user TrackerUser user to map
     * @return UserResponse
     */
    @Mapping(source = "username", target = "name")
    UserResponse toResponse(TrackerUser user);

    /**
     * Maps List of TrackerUser objects into List of UserResponse objects
     * @param users List<TrackerUser>
     * @return List<UserResponse>
     */
    List<UserResponse> toUserResponseList(List<TrackerUser> users);
}