package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.WorkMode;
import ma.ensa.internHub.repositories.InternshipRepository;

public interface InternshipService {


    long countInternshipsByWorkMode(WorkMode workMode);

    long countAllInternships();

    Internship saveInternship(Internship internship);

}