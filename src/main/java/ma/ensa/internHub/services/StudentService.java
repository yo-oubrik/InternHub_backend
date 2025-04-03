package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface StudentService {
    StudentResponse createStudent(StudentRequest request);

    long countStudents();

    List<StudentResponse> getAllStudents();

    void deleteStudentById(UUID id);

    Map<String, Long> countStudentsByMonth();

}