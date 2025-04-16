package ma.ensa.internHub.services.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import ma.ensa.internHub.exception.InvalidVerificationCodeException;
import ma.ensa.internHub.utils.VerificationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.ensa.internHub.domain.entities.PasswordResetToken;
import ma.ensa.internHub.domain.entities.User;
import ma.ensa.internHub.exception.ResourceNotFoundException;
import ma.ensa.internHub.repositories.PasswordResetTokenRepository;
import ma.ensa.internHub.repositories.UserRepository;
import ma.ensa.internHub.services.EmailNotificationService;
import ma.ensa.internHub.services.PasswordResetService;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int TOKEN_EXPIRY_MINUTES = 5;

    @Override
    @Transactional
    public void generateAndSendResetToken(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));

        String token = VerificationCodeGenerator.generateVerificationCode();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES);

        PasswordResetToken passwordResetToken = PasswordResetToken.builder().token(token).expiryDate(expiryDate).email(email).build() ;
        passwordResetTokenRepository.save(passwordResetToken);

        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("resetLink", resetLink);
        templateModel.put("userName", "User");

        emailNotificationService.sendHtmlEmail(
                email,
                "Password Reset Request",
                "password-reset",
                templateModel,
                null
        );
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidVerificationCodeException("Invalid or expired token"));

        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidVerificationCodeException("Token has expired");
        }

       String email = passwordResetToken.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(passwordResetToken);
    }
}