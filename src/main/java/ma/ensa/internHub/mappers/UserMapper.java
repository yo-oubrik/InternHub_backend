package ma.ensa.internHub.mappers;

import org.mapstruct.Mapper;

import ma.ensa.internHub.domain.dto.request.UserDto;
import ma.ensa.internHub.domain.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
}
