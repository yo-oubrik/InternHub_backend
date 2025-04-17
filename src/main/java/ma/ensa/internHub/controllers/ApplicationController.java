package ma.ensa.internHub.controllers;

import lombok.AllArgsConstructor;
import ma.ensa.internHub.domain.dto.request.ApplicationRequest;
import ma.ensa.internHub.domain.dto.response.ApplicationResponse;
import ma.ensa.internHub.domain.enums.ApplicationStatus;
import ma.ensa.internHub.services.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/company/{companyId}/count")
    public ResponseEntity<Long> countApplicationsByCompanyId(@PathVariable UUID companyId) {
        return ResponseEntity.ok(applicationService.countApplicationsByCompanyId(companyId));
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


    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.createApplication(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeApplicationById(@PathVariable UUID id) {
        applicationService.removeApplicationById(id);
        return ResponseEntity.noContent().build();
    }
}