package ma.ensa.internHub.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.request.CompanyUpdateRequest;
import ma.ensa.internHub.domain.dto.request.EmailVerificationRequest;
import ma.ensa.internHub.domain.dto.request.NotificationRequest;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;

public interface CompanyService {
    CompanyResponse createCompany(CompanyRequest request);

    CompanyResponse updateCompany(UUID id, CompanyUpdateRequest request);

    Map<String, Long> countCompaniesByMonth();

    long countCompanies();

    List<CompanyResponse> getAllCompanies();

    void deleteCompanyById(UUID id);

    CompanyResponse confirmAndRegisterCompany(EmailVerificationRequest request);

    void blockCompany(UUID id, NotificationRequest request);

    void unblockCompany(UUID id, NotificationRequest request);

    CompanyResponse getCompanyById(UUID id);

    CompanyResponse getCompanyByEmail(String email);
}
