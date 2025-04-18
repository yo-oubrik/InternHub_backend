package ma.ensa.internHub.repositories;

import ma.ensa.internHub.domain.entities.Certificat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CertificatRepository extends JpaRepository<Certificat, UUID> {
}