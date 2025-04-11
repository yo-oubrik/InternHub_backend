package ma.ensa.internHub.repositories;

import ma.ensa.internHub.domain.entities.PendingCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingCompanyRepository extends JpaRepository<PendingCompany, UUID> {

    boolean existsByEmail(String email);

    Optional<PendingCompany> findByEmail(String email);
}