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

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

}
