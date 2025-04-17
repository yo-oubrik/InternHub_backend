package ma.ensa.internHub.tasks;

import jakarta.transaction.Transactional;
import ma.ensa.internHub.repositories.PasswordResetTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExpiredTokenCleanupTask {

    private final PasswordResetTokenRepository tokenRepository;

    public ExpiredTokenCleanupTask(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(fixedDelay = 300000)
    @Transactional
    public void cleanUpExpiredTokens() {
        tokenRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }
}
