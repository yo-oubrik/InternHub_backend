package ma.ensa.internHub.repositories;

import ma.ensa.internHub.domain.entities.Certificat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CertificatRepository extends JpaRepository<Certificat, UUID> {
}