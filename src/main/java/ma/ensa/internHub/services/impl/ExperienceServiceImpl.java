package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.ExperienceRequest;
import ma.ensa.internHub.domain.dto.response.ExperienceResponse;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.Experience;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.exception.ResourceNotFoundException;
import ma.ensa.internHub.mappers.CompanyMapper;
import ma.ensa.internHub.mappers.ExperienceMapper;
import ma.ensa.internHub.mappers.StudentMapper;
import ma.ensa.internHub.repositories.CompanyRepository;
import ma.ensa.internHub.repositories.ExperienceRepository;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.services.ExperienceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final ExperienceMapper experienceMapper;
    private final StudentMapper studentMapper;
    private final CompanyMapper companyMapper;

    @Override
    public ExperienceResponse createExperience(ExperienceRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Experience experience = experienceMapper.toEntity(request);
        experience.setStudent(student);
        experience.setCompany(company);
        experience = experienceRepository.save(experience);

        ExperienceResponse response = experienceMapper.toResponse(experience);
        response.setStudentResponse(studentMapper.toResponse(student));
        response.setCompanyResponse(companyMapper.toResponse(company));

        return response;
    }

    @Override
    public ExperienceResponse getExperienceById(UUID id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        ExperienceResponse response = experienceMapper.toResponse(experience);
        response.setStudentResponse(studentMapper.toResponse(experience.getStudent()));
        response.setCompanyResponse(companyMapper.toResponse(experience.getCompany()));

        return response;
    }

    @Override
    public List<ExperienceResponse> getAllExperiences() {
        return experienceRepository.findAll().stream()
                .map(experience -> {
                    ExperienceResponse response = experienceMapper.toResponse(experience);
                    response.setStudentResponse(studentMapper.toResponse(experience.getStudent()));
                    response.setCompanyResponse(companyMapper.toResponse(experience.getCompany()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ExperienceResponse updateExperience(UUID id, ExperienceRequest request) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        experienceMapper.updateEntityFromRequest(request, experience);
        experience.setStudent(student);
        experience.setCompany(company);
        experience = experienceRepository.save(experience);

        ExperienceResponse response = experienceMapper.toResponse(experience);
        response.setStudentResponse(studentMapper.toResponse(student));
        response.setCompanyResponse(companyMapper.toResponse(company));

        return response;
    }

    @Override
    public void deleteExperience(UUID id) {
        if (!experienceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Experience not found");
        }
        experienceRepository.deleteById(id);
    }
}
