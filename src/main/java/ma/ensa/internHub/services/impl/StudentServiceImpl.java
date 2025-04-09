package ma.ensa.internHub.services.impl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.EmailVerificationRequest;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;
import ma.ensa.internHub.domain.entities.PendingStudent;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.exception.ExpiredVerificationCodeException;
import ma.ensa.internHub.exception.InvalidVerificationCodeException;
import ma.ensa.internHub.mappers.PendingStudentMapper;
import ma.ensa.internHub.mappers.StudentMapper;
import ma.ensa.internHub.repositories.PendingStudentRepository;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.repositories.UserRepository;
import ma.ensa.internHub.services.EmailNotificationService;
import ma.ensa.internHub.services.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotificationService emailNotificationService;
    private final PendingStudentRepository pendingStudentRepository;
    private final PendingStudentMapper pendingStudentMapper;

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        String studentMail = request.getEmail();
        if (userRepository.existsByEmail(studentMail)) {
            throw new DuplicateResourceException("Email already exists");
        }

        emailNotificationService.sendHtmlEmail(studentMail, "Welcome To InternHub", "welcome-student",
                Map.of("studentName", request.getFirstName()), Map.of("logo.png", "/static/logo.png"));
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
        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };

        for (String month : months) {
            studentCountByMonth.put(month, 0L);
        }

        for (Object[] result : results) {
            int monthIndex = (int) result[0] - 1;
            long count = (long) result[1];
            studentCountByMonth.put(months[monthIndex], count);
        }
        return studentCountByMonth;
    }

    public StudentResponse confirmAndRegisterStudent(EmailVerificationRequest request) {
        PendingStudent pending = pendingStudentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("No verification request found"));

        if (!pending.getConfirmationCode().equals(request.getVerificationCode()))
            throw new InvalidVerificationCodeException("Invalid verification code");

        if (pending.getExpiryDate().isBefore(LocalDateTime.now()))
            throw new ExpiredVerificationCodeException("Verification code has expired");

        Student student = pendingStudentMapper.convertToStudent(pending);

        studentRepository.save(student);
        pendingStudentRepository.delete(pending);

        return studentMapper.toResponse(student);
    }

}
