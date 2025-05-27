package ma.ensa.internHub.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.NotificationRequest;
import ma.ensa.internHub.domain.dto.request.CreateFlagRequest;
import ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview;
import ma.ensa.internHub.domain.dto.response.StudentFlagResponse;
import ma.ensa.internHub.services.StudentFlagService;

@RestController
@RequestMapping("/api/v1/flagged-students")
@RequiredArgsConstructor
public class StudentFlagController {
    private final StudentFlagService studentFlagService;

    @PostMapping
    public void createStudentFlag(@RequestBody CreateFlagRequest request) {
        studentFlagService.createStudentFlag(request);
    }

    @GetMapping("/{id}/flags")
    public List<StudentFlagResponse> getStudentFlagsHistory(@PathVariable UUID id) {
        return studentFlagService.getStudentFlagsHistory(id);
    }

    @GetMapping("/count")
    public long countUnresolvedStudentFlags() {
        return studentFlagService.countUnresolvedStudentFlags();
    }

    @GetMapping("/overview")
    public List<FlaggedStudentOverview> getFlaggedStudentsOverview() {
        return studentFlagService.getFlaggedStudentsOverview();
    }

    @GetMapping("/{id}/unresolved/count")
    public long getUnresolvedStudentFlagsCountById(@PathVariable UUID id) {
        return studentFlagService.getUnresolvedStudentFlagsCountById(id);
    }

    @GetMapping("/{id}/resolved/count")
    public long getResolvedStudentFlagsCountById(@PathVariable UUID id) {
        return studentFlagService.getIgnoredFlagsCountByStudentId(id);
    }

    @GetMapping("/{id}/warnings/count")
    public long getStudentWarningsCount(@PathVariable UUID id) {
        return studentFlagService.getStudentWarningsCount(id);
    }

    @PutMapping("/{id}/resolve")
    public void ignoreStudentFlag(@PathVariable UUID id) {
        studentFlagService.ignoreStudentFlag(id);
    }

    @PostMapping("/{id}/warn")
    public void warnStudent(@PathVariable UUID id, @ModelAttribute NotificationRequest request) {
        studentFlagService.warnStudent(id, request);
    }
}
