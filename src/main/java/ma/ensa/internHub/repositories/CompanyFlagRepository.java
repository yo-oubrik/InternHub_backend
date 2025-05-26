package ma.ensa.internHub.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.ensa.internHub.domain.dto.response.FlaggedCompanyOverview;
import ma.ensa.internHub.domain.entities.CompanyFlag;
import ma.ensa.internHub.domain.enums.ReportStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyFlagRepository extends JpaRepository<CompanyFlag, UUID> {
    List<CompanyFlag> findByFlaggedCompanyId(UUID companyId);

    List<CompanyFlag> findByFlaggedByStudentId(UUID studentId);

    long countByReportStatus(ReportStatus reportStatus);

    long countByFlaggedCompany_IdAndReportStatus(UUID companyId, ReportStatus reportStatus);

    @Query("SELECT new ma.ensa.internHub.domain.dto.response.FlaggedCompanyOverview( " +
            "cf.flaggedCompany.id, cf.flaggedCompany.name, cf.flaggedCompany.email, " +
            "COUNT(cf), MAX(cf.createdAt)) " +
            "FROM CompanyFlag cf " +
            "WHERE cf.reportStatus = :status " +
            "AND cf.flaggedCompany.blocked = false " +
            "GROUP BY cf.flaggedCompany.id, cf.flaggedCompany.name, cf.flaggedCompany.email")
    List<FlaggedCompanyOverview> findFlaggedCompaniesOverviewByStatus(@Param("status") ReportStatus status);
}
