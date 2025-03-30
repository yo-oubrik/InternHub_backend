package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;
import java.util.List;
import java.util.UUID;

public interface CompanyService {
    CompanyResponse createCompany(CompanyRequest request);

    // CompanyResponse updateCompany(UUID id, CompanyRequest request);

    // void deleteCompany(UUID id);

    // CompanyResponse getCompany(UUID id);

    // List<CompanyResponse> getAllCompanies();
}
