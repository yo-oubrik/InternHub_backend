package ma.ensa.internHub.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.CertificatRequest;
import ma.ensa.internHub.domain.dto.response.CertificatResponse;
import ma.ensa.internHub.services.CertificatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/certificates")
@RequiredArgsConstructor
public class CertificatController {

    private final CertificatService certificatService;

    @PostMapping("/create")
    public ResponseEntity<CertificatResponse> createCertificate(@Valid @RequestBody CertificatRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(certificatService.createCertificat(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificatResponse> getCertificateById(@PathVariable UUID id) {
        return ResponseEntity.ok(certificatService.getCertificatById(id));
    }

    @GetMapping
    public ResponseEntity<List<CertificatResponse>> getAllCertificates() {
        return ResponseEntity.ok(certificatService.getAllCertificates());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificatResponse> updateCertificate(
            @PathVariable UUID id,
            @Valid @RequestBody CertificatRequest request) {
        return ResponseEntity.ok(certificatService.updateCertificat(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable UUID id) {
        certificatService.deleteCertificat(id);
        return ResponseEntity.noContent().build();
    }
}