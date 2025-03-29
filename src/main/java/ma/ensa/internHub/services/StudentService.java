package ma.ensa.internHub.services;

import java.util.List;
import java.util.UUID;

import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;

public interface StudentService {
    StudentResponse createStudent(StudentRequest request);

    StudentResponse getStudentByEmail(String email);

    List<StudentResponse> getAllStudents();

    StudentResponse updateStudent(UUID id, StudentRequest request);

    void deleteStudent(UUID id);

    StudentResponse getStudentById(UUID id);
}