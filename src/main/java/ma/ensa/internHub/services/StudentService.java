package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;

public interface StudentService {
    StudentResponse createStudent(StudentRequest request);

}