package ma.ensa.internHub.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.InternshipRequest;
import ma.ensa.internHub.domain.dto.response.InternshipResponse;
import ma.ensa.internHub.domain.entities.WorkMode;
import ma.ensa.internHub.services.InternshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping("/count/remote")
    public long countRemoteInternships() {
        return internshipService.countInternshipsByWorkMode(WorkMode.REMOTE);
    }

    @GetMapping("/count/on-site")
    public long countOnSiteInternships() {
        return internshipService.countInternshipsByWorkMode(WorkMode.ON_SITE);
    }

    @GetMapping("/count")
    public long countAllInternships() {
        return internshipService.countAllInternships();
    }

    @PostMapping
    public ResponseEntity<InternshipResponse> createInternship(@Valid @RequestBody InternshipRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(internshipService.saveInternship(request));
    }

    @GetMapping
    public ResponseEntity<List<InternshipResponse>> getAllInternships() {
        return ResponseEntity.ok(internshipService.getAllInternships());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipResponse> getInternshipById(@PathVariable UUID id) {
        return ResponseEntity.ok(internshipService.getInternshipById(id));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<InternshipResponse>> getInternshipsByCompany(@PathVariable UUID companyId) {
        return ResponseEntity.ok(internshipService.getInternshipsByCompanyId(companyId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternship(@PathVariable UUID id) {
        internshipService.deleteInternship(id);
        return ResponseEntity.noContent().build();
    }

}