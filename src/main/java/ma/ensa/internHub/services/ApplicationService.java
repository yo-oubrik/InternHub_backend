package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.ApplicationRequest;
import ma.ensa.internHub.domain.dto.response.ApplicationResponse;
import ma.ensa.internHub.domain.enums.ApplicationStatus;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    List<ApplicationResponse> getApplicationsByCompanyId(UUID companyId);

    List<ApplicationResponse> getApplicationsByInternshipId(UUID internshipId);

    Long countApplicationsByCompanyId(UUID companyId);

    Long countApplicationsByCompanyIdWithStatus(UUID companyId, ApplicationStatus status);

    ApplicationResponse createApplication(ApplicationRequest request);

    void removeApplicationById(UUID id);
}