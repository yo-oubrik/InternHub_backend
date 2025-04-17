package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.InternshipRequest;
import ma.ensa.internHub.domain.dto.response.InternshipResponse;
import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.WorkMode;

import java.util.List;
import java.util.UUID;


public interface InternshipService {


    long countInternshipsByWorkMode(WorkMode workMode);

    long countAllInternships();

    InternshipResponse saveInternship(InternshipRequest request);
    List<InternshipResponse> getAllInternships();
    InternshipResponse getInternshipById(UUID id);
    List<InternshipResponse> getInternshipsByCompanyId(UUID companyId);
    void deleteInternship(UUID id);
}