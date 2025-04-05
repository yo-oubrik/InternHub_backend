package ma.ensa.internHub.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview;
import ma.ensa.internHub.domain.enums.ReportStatus;
import ma.ensa.internHub.repositories.StudentFlagRepository;
import ma.ensa.internHub.services.StudentFlagService;

@Service
@RequiredArgsConstructor
public class StudentFlagServiceImpl implements StudentFlagService {
    private final StudentFlagRepository studentFlagRepository;

    @Override
    public long countUnresolvedStudentFlags() {
        return studentFlagRepository.countByReportStatus(ReportStatus.UNRESOLVED);
    }

    @Override
    public List<FlaggedStudentOverview> getFlaggedStudentsOverview() {
        return studentFlagRepository.findFlaggedStudentsOverviewByStatus(ReportStatus.UNRESOLVED);
    }

}
