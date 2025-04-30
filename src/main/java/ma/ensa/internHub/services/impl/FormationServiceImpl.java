package ma.ensa.internHub.services.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.FormationRequest;
import ma.ensa.internHub.domain.dto.response.FormationResponse;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.Formation;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.exception.ResourceNotFoundException;
import ma.ensa.internHub.mappers.CompanyMapper;
import ma.ensa.internHub.mappers.FormationMapper;
import ma.ensa.internHub.mappers.StudentMapper;
import ma.ensa.internHub.repositories.CompanyRepository;
import ma.ensa.internHub.repositories.FormationRepository;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.security.SecurityUtils;
import ma.ensa.internHub.services.FormationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormationServiceImpl implements FormationService {

    private final FormationRepository formationRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final FormationMapper formationMapper;
    private final StudentMapper studentMapper;
    private final CompanyMapper companyMapper;
    private final  String email = SecurityUtils.getCurrentUserEmail();

    @Override
    public FormationResponse createFormation(FormationRequest request) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Formation formation = formationMapper.toEntity(request);
        formation.setStudent(student);
        formation.setCompany(company);
        formation = formationRepository.save(formation);

        FormationResponse response = formationMapper.toResponse(formation);
        response.setStudentResponse(studentMapper.toResponse(student));
        response.setCompanyResponse(companyMapper.toResponse(company));

        return response;
    }

    @Override
    public FormationResponse getFormationById(UUID id) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found"));

        FormationResponse response = formationMapper.toResponse(formation);
        response.setStudentResponse(studentMapper.toResponse(formation.getStudent()));
        response.setCompanyResponse(companyMapper.toResponse(formation.getCompany()));

        return response;
    }

    @Override
    public List<FormationResponse> getAllFormations() {
        return formationRepository.findAll().stream()
                .map(formation -> {
                    FormationResponse response = formationMapper.toResponse(formation);
                    response.setStudentResponse(studentMapper.toResponse(formation.getStudent()));
                    response.setCompanyResponse(companyMapper.toResponse(formation.getCompany()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public FormationResponse updateFormation(UUID id, FormationRequest request) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found"));
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        formationMapper.updateEntityFromRequest(request, formation);
        formation.setStudent(student);
        formation.setCompany(company);
        formation = formationRepository.save(formation);

        FormationResponse response = formationMapper.toResponse(formation);
        response.setStudentResponse(studentMapper.toResponse(student));
        response.setCompanyResponse(companyMapper.toResponse(company));

        return response;
    }

    @Override
    public void deleteFormation(UUID id) {
        if (!formationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Formation not found");
        }
        formationRepository.deleteById(id);
    }
}