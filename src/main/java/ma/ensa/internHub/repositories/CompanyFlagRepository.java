package ma.ensa.internHub.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ensa.internHub.domain.entities.CompanyFlag;
import ma.ensa.internHub.domain.enums.ReportStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyFlagRepository extends JpaRepository<CompanyFlag, UUID> {
    List<CompanyFlag> findByFlaggedCompanyId(UUID companyId);

    List<CompanyFlag> findByFlaggedByStudentId(UUID studentId);

    long countByReportStatus(ReportStatus reportStatus);
}
