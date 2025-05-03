package ma.ensa.internHub.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ensa.internHub.domain.entities.PendingStudent;

@Repository
public interface PendingStudentRepository extends JpaRepository<PendingStudent, UUID> {

    Optional<PendingStudent> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByExpiryDateBefore(LocalDateTime dateTime);
}
