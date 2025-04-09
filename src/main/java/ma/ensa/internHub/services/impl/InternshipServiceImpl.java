package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.WorkMode;
import ma.ensa.internHub.repositories.InternshipRepository;
import ma.ensa.internHub.services.InternshipService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternshipServiceImpl implements InternshipService {

    private final InternshipRepository internshipRepository;

    @Override
    public long countInternshipsByWorkMode(WorkMode workMode) {
        return internshipRepository.countByWorkMode(workMode);
    }

    @Override
    public long countAllInternships() {
        return internshipRepository.count();
    }

    @Override
    public Internship saveInternship(Internship internship) {
        return internshipRepository.save(internship);
    }

}