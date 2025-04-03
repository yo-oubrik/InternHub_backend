package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.mappers.CompanyMapper;
import ma.ensa.internHub.repositories.CompanyRepository;
import ma.ensa.internHub.services.CompanyService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CompanyResponse createCompany(CompanyRequest request) {
        if (companyRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        Company company = companyMapper.toEntity(request);
        company.setPassword(passwordEncoder.encode(request.getPassword()));

        companyRepository.save(company);
        return companyMapper.toResponse(company);
    }

    @Override
    public Map<String, Long> countCompaniesByMonth() {
        List<Object[]> results = companyRepository.countCompaniesByMonth();
        Map<String, Long> companyCountByMonth = new LinkedHashMap<>();
        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };

        for (String month : months) {
            companyCountByMonth.put(month, 0L);
        }

        for (Object[] result : results) {
            int monthIndex = (int) result[0] - 1;
            long count = (long) result[1];
            companyCountByMonth.put(months[monthIndex], count);
        }

        return companyCountByMonth;
    }

    @Override
    public long countCompanies() {
        return companyRepository.count();
    }

}
