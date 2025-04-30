package ma.ensa.internHub.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview;
import ma.ensa.internHub.domain.entities.StudentFlag;
import ma.ensa.internHub.domain.enums.ReportStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFlagRepository extends JpaRepository<StudentFlag, UUID> {
    long countByReportStatus(ReportStatus reportStatus);

    long countByFlaggedStudent_IdAndReportStatus(UUID studentId, ReportStatus reportStatus);

    @Query("SELECT new ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview( " +
            "sf.flaggedStudent.id, sf.flaggedStudent.firstName, sf.flaggedStudent.lastName, " +
            "sf.flaggedStudent.email, COUNT(sf), MAX(sf.date)) " +
            "FROM StudentFlag sf " +
            "WHERE sf.reportStatus = :status " +
            "GROUP BY sf.flaggedStudent.id, sf.flaggedStudent.firstName, sf.flaggedStudent.lastName, sf.flaggedStudent.email")
    List<FlaggedStudentOverview> findFlaggedStudentsOverviewByStatus(@Param("status") ReportStatus status);

}
