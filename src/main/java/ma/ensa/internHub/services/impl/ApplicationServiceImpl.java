package ma.ensa.internHub.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ma.ensa.internHub.domain.dto.request.ApplicationRequest;
import ma.ensa.internHub.domain.dto.response.ApplicationResponse;
import ma.ensa.internHub.domain.entities.Application;
import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.domain.enums.ApplicationStatus;
import ma.ensa.internHub.mappers.ApplicationMapper;
import ma.ensa.internHub.mappers.InternshipMapper;
import ma.ensa.internHub.mappers.StudentMapper;
import ma.ensa.internHub.repositories.ApplicationRepository;
import ma.ensa.internHub.repositories.InternshipRepository;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.services.ApplicationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ma.ensa.internHub.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final InternshipRepository internshipRepository;
    private final StudentRepository studentRepository;
    private final ApplicationMapper applicationMapper;
    private final StudentMapper studentMapper;
    private final InternshipMapper internshipMapper;

    @Override
    public List<ApplicationResponse> getApplicationsByCompanyId(UUID companyId) {
        return applicationRepository.findByInternshipCompanyId(companyId).stream()
                .map(application -> {
                    ApplicationResponse response = applicationMapper.toResponse(application);
                    response.setStudentResponse(studentMapper.toResponse(application.getStudent()));
                    response.setInternshipResponse(internshipMapper.toResponse(application.getInternship()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponse> getApplicationsByInternshipId(UUID internshipId) {
        return applicationRepository.findAll().stream()
                .filter(app -> app.getInternship() != null && app.getInternship().getId().equals(internshipId))
                .map(application -> {
                    ApplicationResponse response = applicationMapper.toResponse(application);
                    response.setStudentResponse(studentMapper.toResponse(application.getStudent()));
                    response.setInternshipResponse(internshipMapper.toResponse(application.getInternship()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Long countApplicationsByCompanyId(UUID companyId) {
        return applicationRepository.findAll().stream()
                .filter(app -> app.getInternship() != null &&
                        app.getInternship().getCompany() != null &&
                        app.getInternship().getCompany().getId().equals(companyId))
                .count();
    }

    @Override
    public Long countApplicationsByCompanyIdWithStatus(UUID companyId, ApplicationStatus status) {
        return applicationRepository.findAll().stream()
                .filter(app -> app.getInternship() != null &&
                        app.getInternship().getCompany() != null &&
                        app.getInternship().getCompany().getId().equals(companyId) &&
                        app.getStatus() == status)
                .count();
    }

    @Override
    public ApplicationResponse createApplication(ApplicationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + email));

        Internship internship = internshipRepository.findById(request.getInternshipId())
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found with ID: " + request.getInternshipId()));

        Application application = applicationMapper.toEntity(request);
        application.setStudent(student);
        application.setInternship(internship);

        Application saved = applicationRepository.save(application);

        ApplicationResponse response = applicationMapper.toResponse(saved);
        response.setStudentResponse(studentMapper.toResponse(saved.getStudent()));
        response.setInternshipResponse(internshipMapper.toResponse(saved.getInternship()));
        return response;
    }

    @Override
    public void removeApplicationById(UUID id) {
        if (!applicationRepository.existsById(id)) {
            throw new EntityNotFoundException("Application not found with ID: " + id);
        }
        applicationRepository.deleteById(id);
    }
}
