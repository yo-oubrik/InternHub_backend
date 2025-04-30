package ma.ensa.internHub.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import ma.ensa.internHub.domain.dto.request.EmailVerificationRequest;
import ma.ensa.internHub.domain.dto.request.NotificationRequest;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;

public interface StudentService {
    StudentResponse createStudent(StudentRequest request);

    long countStudents();

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(UUID id);

    StudentResponse getStudentByEmail(String email);

    StudentResponse updateStudentById(UUID id, StudentRequest request);

    Map<String, Long> countStudentsByMonth();

    StudentResponse confirmAndRegisterStudent(EmailVerificationRequest request);

    void blockStudent(UUID id, NotificationRequest request);

    void unblockStudent(UUID id, NotificationRequest request);
}