package ma.ensa.internHub.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.EmailWithAttachmentsRequest;
import ma.ensa.internHub.domain.dto.request.NotificationRequest;
import ma.ensa.internHub.domain.dto.request.CreateFlagRequest;
import ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview;
import ma.ensa.internHub.domain.dto.response.StudentFlagResponse;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.domain.entities.StudentFlag;
import ma.ensa.internHub.domain.enums.ReportStatus;
import ma.ensa.internHub.exception.BadRequestException;
import ma.ensa.internHub.mappers.StudentFlagMapper;
import ma.ensa.internHub.repositories.StudentFlagRepository;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.services.EmailNotificationService;
import ma.ensa.internHub.services.StudentFlagService;
import ma.ensa.internHub.utils.AuthUtils;

@Service
@RequiredArgsConstructor
public class StudentFlagServiceImpl implements StudentFlagService {
    private final StudentFlagRepository studentFlagRepository;
    private final StudentFlagMapper studentFlagMapper;
    private final EmailNotificationService emailNotificationService;
    private final StudentRepository studentRepository;
    private final AuthUtils authUtils;

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
        return studentFlagRepository.countByFlaggedStudent_IdAndReportStatus(id, ReportStatus.RESOLVED);
    }

    @Override
    public long getStudentWarningsCount(UUID id) {
        return studentFlagRepository.countByFlaggedStudent_IdAndReportStatus(id, ReportStatus.WARNED);
    }

    @Override
    public List<StudentFlagResponse> getStudentFlagsHistory(UUID id) {
        return studentFlagRepository.findByFlaggedStudent_Id(id).stream()
                .map(studentFlagMapper::toResponse)
                .toList();
    }

    @Override
    public void ignoreStudentFlag(UUID id) {
        studentFlagRepository.findById(id)
                .map(studentFlag -> {
                    if (!(studentFlag.getReportStatus() == ReportStatus.UNRESOLVED)) {
                        throw new BadRequestException("Flag already resolved or student warned");
                    }
                    studentFlag.setReportStatus(ReportStatus.RESOLVED);
                    return studentFlagRepository.save(studentFlag);
                })
                .orElseThrow(() -> new EntityNotFoundException("Student flag not found with id: " + id));
    }

    @Override
    public void warnStudent(UUID id, NotificationRequest request) {
        var studentFlag = studentFlagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student flag not found with id: " + id));

        if (studentFlag.getReportStatus() != ReportStatus.UNRESOLVED) {
            throw new BadRequestException("Flag already resolved or student warned");
        }

        emailNotificationService.sendDynamicEmailWithMultipartAttachments(
                EmailWithAttachmentsRequest.builder().to(studentFlag.getFlaggedStudent().getEmail())
                        .subject(request.getSubject())
                        .recepientName(studentFlag.getFlaggedStudent().getFirstName())
                        .htmlBody(request.getMessage())
                        .attachments(request.getAttachments()).build());

        studentFlag.setReportStatus(ReportStatus.WARNED);
        studentFlagRepository.save(studentFlag);
    }

    @Override
    public void createStudentFlag(CreateFlagRequest request) {
        Company flaggingCompany = authUtils.getCurrentCompany();
        Student flaggedStudent = studentRepository.findById(request.getTargetId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + request.getTargetId()));

        StudentFlag studentFlag = StudentFlag.builder()
                .flaggedStudent(flaggedStudent)
                .flaggedByCompany(flaggingCompany)
                .reason(request.getReason())
                .description(request.getDescription())
                .screenshots(request.getScreenshots())
                .reportStatus(ReportStatus.UNRESOLVED)
                .build();

        studentFlagRepository.save(studentFlag);
    }
}
