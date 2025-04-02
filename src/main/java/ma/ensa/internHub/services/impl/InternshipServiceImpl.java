package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.entities.WorkMode;
import ma.ensa.internHub.repositories.InternshipRepository;
import ma.ensa.internHub.services.InternshipService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternshipServiceImpl implements InternshipService {

    private final InternshipRepository internshipRepository;

    @Override
    public long countInternshipsByWorkMode(String workMode) {
        WorkMode mode = WorkMode.valueOf(workMode);
        return internshipRepository.countByWorkMode(mode);
    }

    @Override
    public long countAllInternships() {
        return internshipRepository.count();
    }

}