package ma.ensa.internHub.services.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.request.UserRequest;
import ma.ensa.internHub.domain.entities.PendingCompany;
import ma.ensa.internHub.domain.entities.PendingStudent;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.exception.TokenAlreadySentException;
import ma.ensa.internHub.mappers.PendingCompanyMapper;
import ma.ensa.internHub.mappers.PendingStudentMapper;
import ma.ensa.internHub.repositories.PendingCompanyRepository;
import ma.ensa.internHub.repositories.PendingStudentRepository;
import ma.ensa.internHub.repositories.PendingUserRepository;
import ma.ensa.internHub.repositories.UserRepository;
import ma.ensa.internHub.services.EmailNotificationService;
import ma.ensa.internHub.services.PendingUserService;

@RequiredArgsConstructor
@Service
public class PendingUserImpl implements PendingUserService {
    private final PendingUserRepository pendingUserRepository;
    private final PendingStudentRepository pendingStudentRepository;
    private final UserRepository userRepository;
    private final PendingStudentMapper pendingStudentMapper;
    private final PendingCompanyMapper pendingCompanyMapper;
    private final PendingCompanyRepository pendingCompanyRepository;
    private final EmailNotificationService emailService;
    private final PasswordEncoder passwordEncoder;

    private void handlePendingVerificationRequest(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        pendingUserRepository.findByEmail(request.getEmail()).ifPresent(existingPendingUser -> {
            if (existingPendingUser.getExpiryDate().isAfter(LocalDateTime.now())) {
                long secondsRemaining = Duration
                        .between(LocalDateTime.now(), existingPendingUser.getExpiryDate()).getSeconds();
                throw new TokenAlreadySentException(
                        "Verification code already sent. Please try again in " + secondsRemaining + " seconds",
                        secondsRemaining);
            }
            pendingUserRepository.delete(existingPendingUser);
        });
    }

    @Override
    public void processStudentEmailVerification(StudentRequest request) {
        handlePendingVerificationRequest(request);
        PendingStudent pendingStudent = pendingStudentMapper.convertToPendingStudent(request);
        pendingStudent.setPassword(passwordEncoder.encode(request.getPassword()));
        pendingStudentRepository.save(pendingStudent);
        emailService.sendEmailConfirmationCode(request.getEmail(), request.getName(),
                pendingStudent.getConfirmationCode());
    }

    @Override
    public void processCompanyEmailVerification(CompanyRequest request) {
        handlePendingVerificationRequest(request);
        PendingCompany pendingCompany = pendingCompanyMapper.convertToPendingCompany(request);
        pendingCompany.setPassword(passwordEncoder.encode(request.getPassword()));
        pendingCompanyRepository.save(pendingCompany);
        emailService.sendEmailConfirmationCode(request.getEmail(), request.getName(),
                pendingCompany.getConfirmationCode());
    }

    @Override
    public boolean isVerificationInitiated(String email) {
        return pendingUserRepository.existsByEmail(email);
    }

}
