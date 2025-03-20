package website.ylab.financetracker.in.dto.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import website.ylab.financetracker.auth.TrackerUser;

@Mapper()
public interface UserMapper {
    //add instance to call mapper
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "username", target = "username")
    TrackerUser toUser(UserRequest request);
}