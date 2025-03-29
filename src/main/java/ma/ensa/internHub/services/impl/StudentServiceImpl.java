package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.exception.EmptyResourcesException;
import ma.ensa.internHub.mappers.StudentMapper;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.services.StudentService;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        Student student = studentMapper.toEntity(request);
        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

    @Override
    public StudentResponse getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with email: " + email));
        return studentMapper.toResponse(student);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        if (students.isEmpty()) {
            throw new EmptyResourcesException("No students found");
        }
        return students.stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponse updateStudent(UUID id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        studentMapper.updateFromRequest(request, student);
        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

    @Override
    public void deleteStudent(UUID id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    public StudentResponse getStudentById(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        return studentMapper.toResponse(student);
    }
}
