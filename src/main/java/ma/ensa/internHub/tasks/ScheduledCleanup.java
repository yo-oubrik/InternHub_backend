package ma.ensa.internHub.tasks;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.repositories.PasswordResetTokenRepository;
import ma.ensa.internHub.repositories.PendingUserRepository;

@Component
@RequiredArgsConstructor
public class ScheduledCleanup {

    private final PasswordResetTokenRepository tokenRepository;
    private final PendingUserRepository pendingUserRepository;

    @Scheduled(fixedDelay = 1800000)
    @Transactional
    public void cleanUpExpiredTokens() {
        tokenRepository.deleteByExpiryDateBefore(LocalDateTime.now());
        pendingUserRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }
}
