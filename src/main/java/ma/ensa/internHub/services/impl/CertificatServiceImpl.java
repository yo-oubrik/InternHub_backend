package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.CertificatRequest;
import ma.ensa.internHub.domain.dto.response.CertificatResponse;
import ma.ensa.internHub.domain.dto.response.StudentResponse;
import ma.ensa.internHub.domain.entities.Certificat;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.exception.ResourceNotFoundException;
import ma.ensa.internHub.mappers.CertificatMapper;
import ma.ensa.internHub.mappers.StudentMapper;
import ma.ensa.internHub.repositories.CertificatRepository;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.security.SecurityUtils;
import ma.ensa.internHub.services.CertificatService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificatServiceImpl implements CertificatService {

    private final CertificatRepository certificatRepository;
    private final StudentRepository studentRepository;
    private final CertificatMapper certificatMapper;
    private final StudentMapper studentMapper;
    private final  String email = SecurityUtils.getCurrentUserEmail();

    @Override
    public CertificatResponse createCertificat(CertificatRequest request) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Certificat certificat = certificatMapper.toEntity(request);
        certificat.setStudent(student);
        certificat = certificatRepository.save(certificat);

        CertificatResponse response = certificatMapper.toResponse(certificat);
        StudentResponse studentResponse = studentMapper.toResponse(student);
        response.setStudentResponse(studentResponse);

        return response;
    }

    @Override
    public CertificatResponse getCertificatById(UUID id) {
        Certificat certificat = certificatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificat not found"));

        CertificatResponse response = certificatMapper.toResponse(certificat);
        StudentResponse studentResponse = studentMapper.toResponse(certificat.getStudent());
        response.setStudentResponse(studentResponse);

        return response;
    }

    @Override
    public List<CertificatResponse> getAllCertificates() {
        return certificatRepository.findAll().stream()
                .map(certificat -> {
                    CertificatResponse response = certificatMapper.toResponse(certificat);
                    StudentResponse studentResponse = studentMapper.toResponse(certificat.getStudent());
                    response.setStudentResponse(studentResponse);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CertificatResponse updateCertificat(UUID id, CertificatRequest request) {
        Certificat certificat = certificatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificat not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        certificatMapper.updateEntityFromRequest(request, certificat);
        certificat.setStudent(student);
        certificat = certificatRepository.save(certificat);

        CertificatResponse response = certificatMapper.toResponse(certificat);
        StudentResponse studentResponse = studentMapper.toResponse(student);
        response.setStudentResponse(studentResponse);

        return response;
    }

    @Override
    public void deleteCertificat(UUID id) {
        if (!certificatRepository.existsById(id)) {
            throw new ResourceNotFoundException("Certificat not found");
        }
        certificatRepository.deleteById(id);
    }
}