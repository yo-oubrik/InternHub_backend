package ma.ensa.internHub.repositories;

import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.WorkMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InternshipRepository extends JpaRepository<Internship, UUID> {
    long countByWorkMode(WorkMode workMode);

}