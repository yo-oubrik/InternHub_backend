package ma.ensa.pfaproject.service;

import ma.ensa.pfaproject.entities.Company;
import ma.ensa.pfaproject.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService{
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(String id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(String id) {
        companyRepository.deleteById(id);
    }

    @Override
    public int getVerifiedCompaniesCount() {
        return companyRepository.findAll().size();
    }
}
