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

    default UserDto toDto(User user) {
        if (user instanceof Student) {
            return studentToDto((Student) user);
        } else if (user instanceof Company) {
            return companyToDto((Company) user);
        } else if (user instanceof Admin) {
            return adminToDto((Admin) user);
        }
        return null;
    }

    UserDto studentToDto(Student student);

    UserDto companyToDto(Company company);

    @Mapping(target = "blocked", constant = "false")
    @Mapping(target = "blockedAt", expression = "java(null)")
    UserDto adminToDto(Admin admin);
}
