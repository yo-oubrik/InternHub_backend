package ma.ensa.internHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;
import ma.ensa.internHub.domain.entities.Student;

@Mapper(componentModel = "spring", imports = LocalDateTime.class,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    Student toEntity(StudentRequest request);

    StudentResponse toResponse(Student student);

    @Mapping(target = "id", ignore = true)
    void updateFromRequest(StudentRequest request, @MappingTarget Student student);
}