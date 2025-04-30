package ma.ensa.internHub.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview;
import ma.ensa.internHub.domain.dto.response.StudentFlagResponse;
import ma.ensa.internHub.domain.enums.ReportStatus;
import ma.ensa.internHub.mappers.StudentFlagMapper;
import ma.ensa.internHub.repositories.StudentFlagRepository;
import ma.ensa.internHub.services.StudentFlagService;

@Service
@RequiredArgsConstructor
public class StudentFlagServiceImpl implements StudentFlagService {
    private final StudentFlagRepository studentFlagRepository;
    private final StudentFlagMapper studentFlagMapper;

    @Override
    public long countUnresolvedStudentFlags() {
        return studentFlagRepository.countByReportStatus(ReportStatus.UNRESOLVED);
    }

    @Override
    public List<FlaggedStudentOverview> getFlaggedStudentsOverview() {
        return studentFlagRepository.findFlaggedStudentsOverviewByStatus(ReportStatus.UNRESOLVED);
    }

    @Override
    public long getUnresolvedStudentFlagsCountById(UUID id) {
        return studentFlagRepository.countByFlaggedStudent_IdAndReportStatus(id, ReportStatus.UNRESOLVED);
    }

    @Override
    public long getIgnoredFlagsCountByStudentId(UUID id) {
        return studentFlagRepository.countByFlaggedStudent_IdAndReportStatus(id, ReportStatus.IGNORED);
    }

    @Override
    public long getStudentWarningsCount(UUID id) {
        return studentFlagRepository.countByFlaggedStudent_IdAndReportStatus(id, ReportStatus.WARNED);
    }

    @Override
    public List<StudentFlagResponse> getStudentFlagsHistory() {
        return studentFlagRepository.findAll().stream()
                .map(studentFlagMapper::toResponse)
                .toList();
    }

    @Override
    public void ignoreStudentFlag(UUID id) {
        studentFlagRepository.findById(id)
                .map(studentFlag -> {
                    studentFlag.setReportStatus(ReportStatus.IGNORED);
                    return studentFlagRepository.save(studentFlag);
                })
                .orElseThrow(() -> new EntityNotFoundException("Student flag not found with id: " + id));
    }
}
