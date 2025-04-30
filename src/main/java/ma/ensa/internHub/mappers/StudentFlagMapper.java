package ma.ensa.internHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ma.ensa.internHub.domain.dto.response.StudentFlagResponse;
import ma.ensa.internHub.domain.entities.StudentFlag;

@Mapper(componentModel = "spring")
public interface StudentFlagMapper {
    @Mapping(target = "studentId", source = "flaggedStudent.id")
    @Mapping(target = "studentEmail", source = "flaggedStudent.email")
    @Mapping(target = "firstName", source = "flaggedStudent.firstName")
    @Mapping(target = "lastName", source = "flaggedStudent.lastName")
    @Mapping(target = "companyId", source = "flaggedByCompany.id")
    @Mapping(target = "companyEmail", source = "flaggedByCompany.email")
    @Mapping(target = "companyName", source = "flaggedByCompany.name")

    StudentFlagResponse toResponse(StudentFlag student);
}
