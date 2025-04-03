package ma.ensa.internHub.services;

import java.util.List;
import java.util.Map;

import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;

public interface CompanyService {
    CompanyResponse createCompany(CompanyRequest request);

    Map<String, Long> countCompaniesByMonth();

    long countCompanies();

    List<CompanyResponse> getAllCompanies();
}
