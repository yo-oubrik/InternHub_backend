package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.FormationRequest;
import ma.ensa.internHub.domain.dto.response.FormationResponse;

import java.util.List;
import java.util.UUID;

public interface FormationService {

    FormationResponse createFormation(FormationRequest request);

    FormationResponse getFormationById(UUID id);

    List<FormationResponse> getAllFormations();

    FormationResponse updateFormation(UUID id, FormationRequest request);

    void deleteFormation(UUID id);
}