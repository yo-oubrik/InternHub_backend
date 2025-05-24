package ma.ensa.internHub.services.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.entities.PasswordResetToken;
import ma.ensa.internHub.domain.entities.User;
import ma.ensa.internHub.exception.InvalidVerificationCodeException;
import ma.ensa.internHub.exception.ResourceNotFoundException;
import ma.ensa.internHub.exception.TokenAlreadySentException;
import ma.ensa.internHub.repositories.PasswordResetTokenRepository;
import ma.ensa.internHub.repositories.UserRepository;
import ma.ensa.internHub.services.EmailNotificationService;
import ma.ensa.internHub.services.PasswordResetService;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

        private final PasswordResetTokenRepository passwordResetTokenRepository;
        private final UserRepository userRepository;
        private final EmailNotificationService emailNotificationService;
        private final PasswordEncoder passwordEncoder;

        @Value("${app.frontend.url}")
        private String frontendUrl;

        @Override
        @Transactional
        public void generateAndSendResetToken(String email) {
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User with email " + email + " not found"));

                passwordResetTokenRepository.findByEmail(email).ifPresent(existingToken -> {
                        if (existingToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                                long secondsRemaining = Duration
                                                .between(LocalDateTime.now(), existingToken.getExpiryDate())
                                                .getSeconds();
                                throw new TokenAlreadySentException(
                                                "Token already sent. Please try again in " + secondsRemaining
                                                                + " seconds",
                                                secondsRemaining);
                        }
                        passwordResetTokenRepository.delete(existingToken);
                });

                PasswordResetToken newToken = PasswordResetToken.builder()
                                .email(email)
                                .build();

                passwordResetTokenRepository.save(newToken);

                String resetLink = frontendUrl + "/reset-password?token=" + newToken.getToken();

                Map<String, Object> templateModel = Map.of(
                                "resetLink", resetLink,
                                "userName", user.getName());

                emailNotificationService.sendHtmlEmail(
                                user.getEmail(),
                                "Password Reset Request",
                                "password-reset",
                                templateModel,
                                null);
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
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User with email " + email + " not found"));
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);

                passwordResetTokenRepository.delete(passwordResetToken);
        }
}