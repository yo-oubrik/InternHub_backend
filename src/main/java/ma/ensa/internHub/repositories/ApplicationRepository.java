package ma.ensa.internHub.repositories;

import ma.ensa.internHub.domain.entities.Application;
import ma.ensa.internHub.domain.enums.ApplicationStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findByInternshipCompanyId(UUID companyId);

    List<Application> findByInternshipId(UUID internshipId);

    List<Application> findByStudentId(UUID studentId);

    Optional<Application> findByInternshipIdAndStudentId(UUID internshipId, UUID studentId);

    Boolean existsByInternshipIdAndStudentId(UUID internshipId, UUID studentId);

    Long countByInternshipCompanyId(UUID companyId);

    Long countByInternshipId(UUID internshipId);

    Long countByInternshipCompanyIdAndStatus(UUID companyId, ApplicationStatus status);

    @Query("SELECT COUNT(DISTINCT a.student.id) FROM Application a")
    Long countDistinctStudentId();

    @Query("SELECT COUNT(DISTINCT a.student.id) FROM Application a WHERE a.status = :status")
    Long countDistinctStudentIdByInternshipStatus(ApplicationStatus status);

}
