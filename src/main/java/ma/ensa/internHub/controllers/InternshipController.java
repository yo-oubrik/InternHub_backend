package ma.ensa.internHub.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.InternshipRequest;
import ma.ensa.internHub.domain.dto.response.InternshipResponse;
import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.WorkMode;
import ma.ensa.internHub.mappers.InternshipMapper;
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
    private final InternshipMapper internshipMapper;

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


    @PostMapping("/create")
    public ResponseEntity<InternshipResponse> createInternship(@Valid @RequestBody InternshipRequest internshipRequest) {
        Internship internship = internshipMapper.toEntity(internshipRequest);
        Internship savedInternship = internshipService.saveInternship(internship);
        InternshipResponse response = internshipMapper.toResponse(savedInternship);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<InternshipResponse>> getAllInternships() {
        List<Internship> internships = internshipService.getAllInternships();
        List<InternshipResponse> responses = internships.stream()
                .map(internshipMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternshipResponse> updateInternship(
            @PathVariable UUID id,
            @Valid @RequestBody InternshipRequest internshipRequest) {

        Internship existingInternship = internshipService.getInternshipById(id);

        internshipMapper.updateFromRequest(internshipRequest, existingInternship);

        Internship updatedInternship = internshipService.saveInternship(existingInternship);

        InternshipResponse response = internshipMapper.toResponse(updatedInternship);

        return ResponseEntity.ok(response);
    }


}