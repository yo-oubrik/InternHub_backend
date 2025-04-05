package ma.ensa.internHub.services;

import java.util.List;

import ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview;

public interface StudentFlagService {
    long countUnresolvedStudentFlags();

    List<FlaggedStudentOverview> getFlaggedStudentsOverview();
}
