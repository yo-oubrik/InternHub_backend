package ma.ensa.internHub.services;

import java.util.List;
import java.util.UUID;

import ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview;

public interface StudentFlagService {
    long countUnresolvedStudentFlags();

    List<FlaggedStudentOverview> getFlaggedStudentsOverview();

    long getUnresolvedStudentFlagsCountById(UUID id);

    long getResolvedStudentFlagsCountById(UUID id);

    long getStudentWarningsCount(UUID id);
}
