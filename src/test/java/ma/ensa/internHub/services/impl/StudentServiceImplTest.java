package ma.ensa.internHub.services.impl;

import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.mappers.StudentMapper;
import ma.ensa.internHub.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private UUID testId;
    private Student testStudent;
    private StudentRequest testRequest;
    private StudentResponse testResponse;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testStudent = new Student();
        testRequest = new StudentRequest();
        testResponse = new StudentResponse();
    }

    @Test
    void createStudent_Success() {
        when(studentMapper.toEntity(testRequest)).thenReturn(testStudent);
        when(studentRepository.save(testStudent)).thenReturn(testStudent);
        when(studentMapper.toResponse(testStudent)).thenReturn(testResponse);

        StudentResponse result = studentService.createStudent(testRequest);

        assertNotNull(result);
        verify(studentRepository).save(testStudent);
        verify(studentMapper).toEntity(testRequest);
        verify(studentMapper).toResponse(testStudent);
    }

    @Test
    void getStudentByEmail_Success() {
        when(studentRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testStudent));
        when(studentMapper.toResponse(testStudent)).thenReturn(testResponse);

        StudentResponse result = studentService.getStudentByEmail("test@example.com");

        assertNotNull(result);
        verify(studentRepository).findByEmail("test@example.com");
        verify(studentMapper).toResponse(testStudent);
    }

    @Test
    void getStudentByEmail_NotFound() {
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.getStudentByEmail("nonexistent@example.com"));
    }

    @Test
    void getAllStudents_Success() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(testStudent));

        var result = studentService.getAllStudents();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(studentRepository).findAll();
    }

    @Test
    void updateStudent_Success() {
        when(studentRepository.findById(testId)).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(testStudent)).thenReturn(testStudent);
        when(studentMapper.toResponse(testStudent)).thenReturn(testResponse);

        StudentResponse result = studentService.updateStudent(testId, testRequest);

        assertNotNull(result);
        verify(studentRepository).findById(testId);
        verify(studentMapper).updateFromRequest(testRequest, testStudent);
        verify(studentRepository).save(testStudent);
        verify(studentMapper).toResponse(testStudent);
    }

    @Test
    void deleteStudent_Success() {
        when(studentRepository.existsById(any())).thenReturn(true);

        assertDoesNotThrow(() -> studentService.deleteStudent(testId));
        verify(studentRepository).deleteById(testId);
    }

    @Test
    void deleteStudent_NotFound() {
        when(studentRepository.existsById(any())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> studentService.deleteStudent(testId));
    }

    @Test
    void getStudentById_Success() {
        when(studentRepository.findById(any())).thenReturn(Optional.of(testStudent));
        when(studentMapper.toResponse(testStudent)).thenReturn(testResponse);
        StudentResponse result = studentService.getStudentById(testId);

        assertNotNull(result);
        verify(studentRepository).findById(testId);
    }

    @Test
    void getStudentById_NotFound() {
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.getStudentById(testId));
    }
}
