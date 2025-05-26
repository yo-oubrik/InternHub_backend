package ma.ensa.internHub.services.impl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ensa.internHub.domain.dto.request.EmailVerificationRequest;
import ma.ensa.internHub.domain.dto.request.EmailWithAttachmentsRequest;
import ma.ensa.internHub.domain.dto.request.NotificationRequest;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.request.StudentUpdateRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;
import ma.ensa.internHub.domain.entities.Certificat;
import ma.ensa.internHub.domain.entities.Experience;
import ma.ensa.internHub.domain.entities.Formation;
import ma.ensa.internHub.domain.entities.PendingStudent;
import ma.ensa.internHub.domain.entities.Project;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.exception.BadRequestException;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.exception.ExpiredVerificationCodeException;
import ma.ensa.internHub.exception.InvalidVerificationCodeException;
import ma.ensa.internHub.mappers.PendingStudentMapper;
import ma.ensa.internHub.mappers.StudentMapper;
import ma.ensa.internHub.repositories.CertificatRepository;
import ma.ensa.internHub.repositories.ExperienceRepository;
import ma.ensa.internHub.repositories.FormationRepository;
import ma.ensa.internHub.repositories.PendingStudentRepository;
import ma.ensa.internHub.repositories.ProjectRepository;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.repositories.UserRepository;
import ma.ensa.internHub.services.EmailNotificationService;
import ma.ensa.internHub.services.StudentService;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ExperienceRepository experienceRepository;
    private final FormationRepository formationRepository;
    private final ProjectRepository projectRepository;
    private final CertificatRepository certificatRepository;
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
                Map.of("studentName", request.getFirstName()), null);
        Student student = studentMapper.toEntity(request);
        student.setPassword(passwordEncoder.encode(request.getPassword()));

        studentRepository.save(student);
        includeAssociations(student);
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
    public StudentResponse getStudentById(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        includeAssociations(student);
        return studentMapper.toResponse(student);
    }

    @Override
    public StudentResponse getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        includeAssociations(student);
        return studentMapper.toResponse(student);
    }

    @Override
    public StudentResponse updateStudentById(UUID id, StudentUpdateRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String currentEmail = authentication.getName();
        Student currentStudent = studentRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        if (!currentStudent.getId().equals(id))
            throw new BadRequestException("You can only update your own account");
        studentMapper.updateFromRequest(request, currentStudent);
        currentStudent = studentRepository.save(currentStudent);
        return studentMapper.toResponse(currentStudent);
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

    @Override
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

    @Override
    public void blockStudent(UUID id, NotificationRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        if (student.isBlocked()) {
            throw new BadRequestException("Student is already blocked");
        }

        emailNotificationService.sendDynamicEmailWithMultipartAttachments(
                EmailWithAttachmentsRequest.builder()
                        .to(student.getEmail())
                        .subject(request.getSubject())
                        .recepientName(student.getFirstName())
                        .htmlBody(request.getMessage())
                        .attachments(request.getAttachments())
                        .build());

        student.setBlocked(true);
        student.setBlockedAt(LocalDateTime.now());
        studentRepository.save(student);
    }

    @Override
    public void unblockStudent(UUID id, NotificationRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        if (!student.isBlocked()) {
            throw new BadRequestException("Student is not blocked");
        }

        emailNotificationService.sendDynamicEmailWithMultipartAttachments(
                EmailWithAttachmentsRequest.builder()
                        .to(student.getEmail())
                        .subject(request.getSubject())
                        .recepientName(student.getFirstName())
                        .htmlBody(request.getMessage())
                        .attachments(request.getAttachments())
                        .build());

        student.setBlocked(false);
        student.setBlockedAt(null);
        studentRepository.save(student);
    }

    private void includeAssociations(Student student) {
        List<Experience> experiences = experienceRepository.findByStudentId(student.getId());
        student.setExperiences(experiences);
        List<Formation> formations = formationRepository.findByStudentId(student.getId());
        student.setFormations(formations);
        List<Project> projects = projectRepository.findByStudentId(student.getId());
        student.setProjects(projects);
        List<Certificat> certificates = certificatRepository.findByStudentId(student.getId());
        student.setCertificates(certificates);
    }

    @Override
    public long countBlockedStudents() {
        return studentRepository.countByBlockedTrue();
    }
}
