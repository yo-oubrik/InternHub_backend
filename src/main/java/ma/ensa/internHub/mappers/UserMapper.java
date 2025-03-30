package ma.ensa.internHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ma.ensa.internHub.domain.dto.request.UserDto;
import ma.ensa.internHub.domain.entities.Admin;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.domain.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", constant = "STUDENT")
    UserDto toDto(Student student);

    @Mapping(target = "role", constant = "COMPANY")
    @Mapping(target = "profilePicture", constant = "")
    UserDto toDto(Company company);

    @Mapping(target = "role", constant = "ADMIN")
    UserDto toDto(Admin company);

    default UserDto toDto(User user) {
        if (user instanceof Student) {
            return toDto((Student) user);
        } else if (user instanceof Company) {
            return toDto((Company) user);
        } else if (user instanceof Admin) {
            return toDto((Admin) user);
        }
        return null;
    }
}
