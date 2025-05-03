package ma.ensa.internHub.repositories;

import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.WorkMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, UUID> {
    long countByWorkMode(WorkMode workMode);

    List<Internship> findByCompanyId(UUID companyId);

}