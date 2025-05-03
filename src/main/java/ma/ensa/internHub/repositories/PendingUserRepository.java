package ma.ensa.internHub.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ensa.internHub.domain.entities.PendingUser;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingUserRepository extends JpaRepository<PendingUser, UUID> {
    boolean existsByEmail(String email);

    Optional<PendingUser> findByEmail(String email);

    void deleteByExpiryDateBefore(LocalDateTime dateTime);

}
