package ma.ensa.internHub.controllers;

import lombok.AllArgsConstructor;
import ma.ensa.internHub.domain.dto.request.ApplicationRequest;
import ma.ensa.internHub.domain.dto.request.NotificationRequest;
import ma.ensa.internHub.domain.dto.response.ApplicationResponse;
import ma.ensa.internHub.domain.enums.ApplicationStatus;
import ma.ensa.internHub.services.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByCompanyId(@PathVariable UUID companyId) {
        return ResponseEntity.ok(applicationService.getApplicationsByCompanyId(companyId));
    }

    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByInternshipId(@PathVariable UUID internshipId) {
        return ResponseEntity.ok(applicationService.getApplicationsByInternshipId(internshipId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ApplicationResponse>> getStudentApplications(@PathVariable UUID studentId) {
        return ResponseEntity.ok(applicationService.getApplicationsByStudentId(studentId));
    }
    

    // Added end point to see if the applicant is already applied to this internship
    // TODO
    @GetMapping("/internship/{internshipId}/student/{studentId}")
    public ResponseEntity<Boolean> isAppliedToThisInternship(
            @PathVariable UUID internshipId,
            @PathVariable UUID studentId) {
        return ResponseEntity.ok(applicationService.isAppliedToThisInternship(internshipId, studentId));
    }

    @GetMapping("/company/{companyId}/count")
    public ResponseEntity<Long> countApplicationsByCompanyId(@PathVariable UUID companyId) {
        return ResponseEntity.ok(applicationService.countApplicationsByCompanyId(companyId));
    }

    // Added end point to count applications by internship TODO
    @GetMapping("/count/internship/{internshipId}")
    public ResponseEntity<Long> countApplicationsByInternshipId(@PathVariable UUID internshipId) {
        return ResponseEntity.ok(applicationService.countApplicationsByInternshipId(internshipId));
    }

    /**
     * Generic endpoint to count applications by company and status.
     * Example: GET /company/{companyId}/count/status?status=PENDING
     */
    @GetMapping("/{companyId}/count/status")
    public ResponseEntity<Long> countApplicationsByCompanyIdWithStatus(
            @PathVariable UUID companyId,
            @RequestParam ApplicationStatus status) {
        long count = applicationService.countApplicationsByCompanyIdWithStatus(companyId, status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/students")
    public ResponseEntity<Long> countDistinctStudents() {
        return ResponseEntity.ok(applicationService.countDistinctStudents());
    }

    @GetMapping("/count/students/{status}")
    public ResponseEntity<Long> countDistinctStudentsByInternshipStatus(@PathVariable ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.countDistinctStudentsByInternshipStatus(status));
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.createApplication(request));
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<Void> acceptApplication(
            @PathVariable UUID id,
            @ModelAttribute NotificationRequest request) {
        applicationService.acceptApplication(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> rejectApplication(
            @PathVariable UUID id,
            @ModelAttribute NotificationRequest request) {
        applicationService.rejectApplication(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeApplicationById(@PathVariable UUID id) {
        applicationService.removeApplicationById(id);
        return ResponseEntity.noContent().build();
    }
}