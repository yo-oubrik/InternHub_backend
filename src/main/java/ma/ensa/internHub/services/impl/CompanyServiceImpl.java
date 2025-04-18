package ma.ensa.internHub.services.impl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.request.EmailVerificationRequest;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.PendingCompany;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.exception.ExpiredVerificationCodeException;
import ma.ensa.internHub.exception.InvalidVerificationCodeException;
import ma.ensa.internHub.mappers.CompanyMapper;
import ma.ensa.internHub.mappers.PendingCompanyMapper;
import ma.ensa.internHub.repositories.CompanyRepository;
import ma.ensa.internHub.repositories.PendingCompanyRepository;
import ma.ensa.internHub.repositories.UserRepository;
import ma.ensa.internHub.services.CompanyService;
import ma.ensa.internHub.services.EmailNotificationService;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMapper companyMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotificationService emailNotificationService;
    private final PendingCompanyRepository pendingCompanyRepository;
    private final PendingCompanyMapper pendingCompanyMapper;

    @Override
    public CompanyResponse createCompany(CompanyRequest request) {
        String companyMail = request.getEmail();
        if (userRepository.existsByEmail(companyMail)) {
            throw new DuplicateResourceException("Email already exists");
        }
        emailNotificationService.sendHtmlEmail(companyMail, "Welcome To InternHub", "welcome-company",
                Map.of("companyName", request.getName()), null);
        Company company = companyMapper.toEntity(request);
        company.setPassword(passwordEncoder.encode(request.getPassword()));
        companyRepository.save(company);

        return companyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse getCompanyById(UUID id) {
        return companyRepository.findById(id)
                .map(companyMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
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

    @Override
    @Transactional
    public CompanyResponse confirmAndRegisterCompany(EmailVerificationRequest request) {
        PendingCompany pendingCompany = pendingCompanyRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("No pending verification found for this email"));

        if (!pendingCompany.getConfirmationCode().equals(request.getVerificationCode())) {
            throw new InvalidVerificationCodeException("Invalid confirmation code");
        }

        if (pendingCompany.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ExpiredVerificationCodeException("Verification code has expired");
        }

        Company company = pendingCompanyMapper.convertToCompany(pendingCompany);

        companyRepository.save(company);
        pendingCompanyRepository.delete(pendingCompany);

        return companyMapper.toResponse(company);
    }

}
