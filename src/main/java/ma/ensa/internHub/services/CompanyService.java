package ma.ensa.internHub.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.request.EmailVerificationRequest;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;
import ma.ensa.internHub.domain.dto.response.StudentResponse;

public interface CompanyService {
    CompanyResponse createCompany(CompanyRequest request);

    Map<String, Long> countCompaniesByMonth();

    long countCompanies();

    CompanyResponse getCompanyById(UUID id);

    List<CompanyResponse> getAllCompanies();

    void deleteCompanyById(UUID id);

    CompanyResponse confirmAndRegisterCompany(EmailVerificationRequest request);

}
