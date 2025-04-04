package ma.ensa.internHub.services.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.mappers.CompanyMapper;
import ma.ensa.internHub.repositories.CompanyRepository;
import ma.ensa.internHub.services.CompanyService;
import ma.ensa.internHub.services.EmailNotificationService;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotificationService emailNotificationService;

    @Override
    public CompanyResponse createCompany(CompanyRequest request) {
        String companyMail = request.getEmail();
        if (companyRepository.existsByEmail(companyMail)) {
            throw new DuplicateResourceException("Email already exists");
        }
        emailNotificationService.sendHtmlEmail(companyMail, "Welcome To InternHub", "welcome-company",
                Map.of("companyName", request.getName()), Map.of("logo.png", "/static/logo.png"));
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

    @Override
    public List<CompanyResponse> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompanyById(UUID id) {
        companyRepository.deleteById(id);
    }

}
