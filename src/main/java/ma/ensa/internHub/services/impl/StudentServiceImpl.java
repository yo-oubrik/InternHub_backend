package ma.ensa.internHub.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.mappers.StudentMapper;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.services.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        Student student = studentMapper.toEntity(request);
        student.setPassword(passwordEncoder.encode(request.getPassword()));

        studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

}
