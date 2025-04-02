
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

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public long countStudents() {
        return studentRepository.count();
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStudentById(UUID id) {
        studentRepository.deleteById(id);
    }


    @Override
    public Map<String, Long> countStudentsByMonth() {
        List<Object[]> results = studentRepository.countStudentsByMonth();
        Map<String, Long> studentCountByMonth = new LinkedHashMap<>();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        // Initialize the map with all months set to 0
        for (String month : months) {
            studentCountByMonth.put(month, 0L);
        }

        // Update the map with actual counts
        for (Object[] result : results) {
            int monthIndex = (int) result[0] - 1;
            long count = (long) result[1];
            studentCountByMonth.put(months[monthIndex], count);
        }
        return studentCountByMonth;
    }

}
