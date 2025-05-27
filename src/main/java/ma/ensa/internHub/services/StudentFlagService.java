package ma.ensa.internHub.services;

import java.util.List;
import java.util.UUID;

import ma.ensa.internHub.domain.dto.request.NotificationRequest;
import ma.ensa.internHub.domain.dto.request.CreateFlagRequest;
import ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview;
import ma.ensa.internHub.domain.dto.response.StudentFlagResponse;

public interface StudentFlagService {
    void createStudentFlag(CreateFlagRequest request);

    long countUnresolvedStudentFlags();

    List<FlaggedStudentOverview> getFlaggedStudentsOverview();

    long getUnresolvedStudentFlagsCountById(UUID id);

    long getIgnoredFlagsCountByStudentId(UUID id);

    long getStudentWarningsCount(UUID id);

    List<StudentFlagResponse> getStudentFlagsHistory(UUID id);

    void ignoreStudentFlag(UUID id);

    void warnStudent(UUID id, NotificationRequest request);
}
