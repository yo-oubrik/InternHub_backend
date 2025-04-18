package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.ExperienceRequest;
import ma.ensa.internHub.domain.dto.response.ExperienceResponse;

import java.util.List;
import java.util.UUID;

public interface ExperienceService {
    ExperienceResponse createExperience(ExperienceRequest request);
    ExperienceResponse getExperienceById(UUID id);
    List<ExperienceResponse> getAllExperiences();
    ExperienceResponse updateExperience(UUID id, ExperienceRequest request);
    void deleteExperience(UUID id);
}