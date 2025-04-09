package ma.ensa.internHub.mappers;

import java.time.LocalDateTime;
import java.util.Random;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.entities.PendingStudent;
import ma.ensa.internHub.domain.entities.Student;

@Mapper(componentModel = "spring", imports = { LocalDateTime.class, Random.class })
public interface PendingStudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "confirmationCode", ignore = true)
    @Mapping(target = "expiryDate", expression = "java(java.time.LocalDateTime.now().plusMinutes(5))")
    @Mapping(target = "password", ignore = true)
    PendingStudent convertToPendingStudent(StudentRequest user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "companyFlagsMade", ignore = true)
    @Mapping(target = "studentFlagsReceived", ignore = true)
    Student convertToStudent(PendingStudent pendingStudent);
}
