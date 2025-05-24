package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.ApplicationRequest;
import ma.ensa.internHub.domain.dto.request.NotificationRequest;
import ma.ensa.internHub.domain.dto.response.ApplicationResponse;
import ma.ensa.internHub.domain.enums.ApplicationStatus;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    List<ApplicationResponse> getApplicationsByCompanyId(UUID companyId);

    List<ApplicationResponse> getApplicationsByInternshipId(UUID internshipId);

    Boolean isAppliedToThisInternship(UUID internshipId, UUID studentId);

    Long countApplicationsByCompanyId(UUID companyId);

    Long countApplicationsByInternshipId(UUID internshipId);

    Long countApplicationsByCompanyIdWithStatus(UUID companyId, ApplicationStatus status);

    Long countDistinctStudents();

    Long countDistinctStudentsByInternshipStatus(ApplicationStatus status);

    ApplicationResponse createApplication(ApplicationRequest request);

    List<ApplicationResponse> getApplicationsByStudentId(UUID studentId);

    void acceptApplication(UUID id, NotificationRequest request);

    void rejectApplication(UUID id, NotificationRequest request);

    void removeApplicationById(UUID id);

}