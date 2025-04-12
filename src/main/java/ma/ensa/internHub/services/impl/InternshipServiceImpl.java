package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.WorkMode;
import ma.ensa.internHub.exception.ResourceNotFoundException;
import ma.ensa.internHub.repositories.InternshipRepository;
import ma.ensa.internHub.services.InternshipService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class
InternshipServiceImpl implements InternshipService {

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

    @Override
    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    public Internship getInternshipById(UUID id) {
        return internshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found with id: " + id));
    }

    @Override
    public List<Internship> getInternshipsByCompanyId(UUID companyId) {
        return internshipRepository.findByCompanyId(companyId);
    }


}