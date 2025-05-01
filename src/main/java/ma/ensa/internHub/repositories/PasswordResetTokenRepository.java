package ma.ensa.internHub.repositories;

import ma.ensa.internHub.domain.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    void deleteByExpiryDateBefore(LocalDateTime dateTime);

    Optional<PasswordResetToken> findByEmail(String email);
}
