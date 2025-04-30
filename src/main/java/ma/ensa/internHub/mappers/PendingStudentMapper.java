package ma.ensa.internHub.mappers;

import java.time.LocalDateTime;
import java.util.Random;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.entities.PendingStudent;
import ma.ensa.internHub.domain.entities.Student;

@Mapper(componentModel = "spring", imports = { LocalDateTime.class,
        Random.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PendingStudentMapper {

    PendingStudent convertToPendingStudent(StudentRequest user);

    @Mapping(target = "id", ignore = true)
    Student convertToStudent(PendingStudent pendingStudent);
}
