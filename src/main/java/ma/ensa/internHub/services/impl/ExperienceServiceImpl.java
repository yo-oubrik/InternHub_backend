package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.ExperienceRequest;
import ma.ensa.internHub.domain.dto.response.ExperienceResponse;
import ma.ensa.internHub.domain.entities.Experience;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.exception.ResourceNotFoundException;
import ma.ensa.internHub.mappers.ExperienceMapper;
import ma.ensa.internHub.repositories.ExperienceRepository;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.services.ExperienceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final StudentRepository studentRepository;
    private final ExperienceMapper experienceMapper;

    @Override
    public ExperienceResponse createExperience(ExperienceRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Experience experience = experienceMapper.toEntity(request);
        experience.setStudent(student);
        experience = experienceRepository.save(experience);

        ExperienceResponse response = experienceMapper.toResponse(experience);

        return response;
    }

    @Override
    public ExperienceResponse getExperienceById(UUID id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        ExperienceResponse response = experienceMapper.toResponse(experience);

        return response;
    }

    @Override
    public List<ExperienceResponse> getAllExperiences() {
        return experienceRepository.findAll().stream()
                .map(experienceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExperienceResponse updateExperience(UUID id, ExperienceRequest request) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        experienceMapper.updateEntityFromRequest(request, experience);
        experience.setStudent(student);
        experience = experienceRepository.save(experience);

        ExperienceResponse response = experienceMapper.toResponse(experience);

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
