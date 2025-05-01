package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.InternshipRequest;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;
import ma.ensa.internHub.domain.dto.response.InternshipResponse;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.WorkMode;
import ma.ensa.internHub.exception.ResourceNotFoundException;
import ma.ensa.internHub.mappers.CompanyMapper;
import ma.ensa.internHub.mappers.InternshipMapper;
import ma.ensa.internHub.repositories.CompanyRepository;
import ma.ensa.internHub.repositories.InternshipRepository;
import ma.ensa.internHub.security.SecurityUtils;
import ma.ensa.internHub.services.InternshipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class
InternshipServiceImpl implements InternshipService {

    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final InternshipMapper internshipMapper;
    private final CompanyMapper companyMapper;
    private final String email = SecurityUtils.getCurrentUserEmail();

    @Override
    public long countInternshipsByWorkMode(WorkMode workMode) {
        return internshipRepository.countByWorkMode(workMode);
    }

    @Override
    public long countAllInternships() {
        return internshipRepository.count();
    }

    @Override
    @Transactional
    public InternshipResponse saveInternship(InternshipRequest request) {

        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company not found with email: " + email));

        Internship internship = internshipMapper.toEntity(request);
        internship.setCompany(company);
        Internship savedInternship = internshipRepository.save(internship);

        CompanyResponse companyResponse = companyMapper.toResponse(company);
        InternshipResponse response = internshipMapper.toResponse(savedInternship);
        response.setCompanyResponse(companyResponse);
        return response ;
    }

    @Override
    public List<InternshipResponse> getAllInternships() {
        return internshipRepository.findAll().stream()
                .map(internship -> {
                    InternshipResponse response = internshipMapper.toResponse(internship);
                    Company company = internship.getCompany();
                    CompanyResponse companyResponse = companyMapper.toResponse(company);
                    response.setCompanyResponse(companyResponse);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public InternshipResponse getInternshipById(UUID id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found with id: " + id));

        InternshipResponse response = internshipMapper.toResponse(internship);
        Company company = internship.getCompany();
        CompanyResponse companyResponse = companyMapper.toResponse(company);

        response.setCompanyResponse(companyResponse);

        return response;
    }

    @Override
    public List<InternshipResponse> getInternshipsByCompanyId(UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));

        CompanyResponse companyResponse = companyMapper.toResponse(company);

        return internshipRepository.findByCompanyId(companyId).stream()
                .map(internship -> {
                    InternshipResponse response = internshipMapper.toResponse(internship);
                    response.setCompanyResponse(companyResponse);
                    return response;
                })
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteInternship(UUID id) {
        if (!internshipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Internship not found with id: " + id);
        }
        internshipRepository.deleteById(id);
    }


}