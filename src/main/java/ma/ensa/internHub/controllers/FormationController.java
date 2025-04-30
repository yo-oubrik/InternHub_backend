package ma.ensa.internHub.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.FormationRequest;
import ma.ensa.internHub.domain.dto.response.FormationResponse;
import ma.ensa.internHub.services.FormationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/formations")
public class FormationController {

    private final FormationService formationService;

    @PostMapping("/create")
    public ResponseEntity<FormationResponse> createFormation(@RequestBody FormationRequest request) {
        FormationResponse response = formationService.createFormation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormationResponse> getFormationById(@PathVariable UUID id) {
        FormationResponse response = formationService.getFormationById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FormationResponse>> getAllFormations() {
        List<FormationResponse> responses = formationService.getAllFormations();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormationResponse> updateFormation(@PathVariable UUID id,
            @RequestBody FormationRequest request) {
        FormationResponse response = formationService.updateFormation(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable UUID id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }
}