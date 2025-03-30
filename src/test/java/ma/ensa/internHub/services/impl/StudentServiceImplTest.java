package ma.ensa.internHub.services.impl;

import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.mappers.StudentMapper;
import ma.ensa.internHub.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentServiceImpl studentService;

    private StudentRequest studentRequest;
    private Student student;
    private UUID studentId;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
        studentRequest = new StudentRequest();
        studentRequest.setEmail("test@test.com");
        studentRequest.setPassword("password");

        student = new Student();
        student.setId(studentId);
        student.setEmail("test@test.com");
    }

    @Test
    void createStudent_Success() {
        when(studentRepository.existsByEmail(any())).thenReturn(false);
        when(studentMapper.toEntity(any())).thenReturn(student);
        when(studentRepository.save(any())).thenReturn(student);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        studentService.createStudent(studentRequest);

        verify(studentRepository).save(any());
    }

    @Test
    void createStudent_DuplicateEmail() {
        when(studentRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> studentService.createStudent(studentRequest));
    }

    @Test
    void getStudentById_Success() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        studentService.getStudentById(studentId);

        verify(studentMapper).toResponse(student);
    }

    @Test
    void getStudentById_NotFound() {
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.getStudentById(studentId));
    }

    @Test
    void getAllStudents_Success() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));

        studentService.getAllStudents();

        verify(studentMapper).toResponse(student);
    }

    @Test
    void deleteStudent_Success() {
        when(studentRepository.existsById(studentId)).thenReturn(true);

        studentService.deleteStudent(studentId);

        verify(studentRepository).deleteById(studentId);
    }
}
