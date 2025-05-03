package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.CertificatRequest;
import ma.ensa.internHub.domain.dto.response.CertificatResponse;

import java.util.List;
import java.util.UUID;

public interface CertificatService {
    CertificatResponse createCertificat(CertificatRequest request);
    CertificatResponse getCertificatById(UUID id);
    List<CertificatResponse> getAllCertificates();
    CertificatResponse updateCertificat(UUID id, CertificatRequest request);
    void deleteCertificat(UUID id);
}