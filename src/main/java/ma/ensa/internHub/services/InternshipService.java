package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.entities.WorkMode;

public interface InternshipService {
    long countInternshipsByWorkMode(WorkMode workMode);

    long countAllInternships();
}