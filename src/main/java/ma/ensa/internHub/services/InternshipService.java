package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.WorkMode;

import java.util.List;
import java.util.UUID;


public interface InternshipService {


    long countInternshipsByWorkMode(WorkMode workMode);

    long countAllInternships();

    Internship saveInternship(Internship internship);

    List<Internship> getAllInternships();

    Internship getInternshipById(UUID id);

    List<Internship> getInternshipsByCompanyId(UUID companyId);

}