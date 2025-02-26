package ma.ensa.pfaproject.service;

import ma.ensa.pfaproject.entities.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyById(String id);
    Company saveCompany(Company company);
    void deleteCompany(String id);

    int getVerifiedCompaniesCount();
}
