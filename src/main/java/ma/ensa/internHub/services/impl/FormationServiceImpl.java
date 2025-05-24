package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.FormationRequest;
import ma.ensa.internHub.domain.dto.response.FormationResponse;
import ma.ensa.internHub.domain.entities.Formation;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.exception.ResourceNotFoundException;
import ma.ensa.internHub.mappers.FormationMapper;
import ma.ensa.internHub.repositories.FormationRepository;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.services.FormationService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormationServiceImpl implements FormationService {

    private final FormationRepository formationRepository;
    private final StudentRepository studentRepository;
    private final FormationMapper formationMapper;

    @Override
    public FormationResponse createFormation(FormationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Formation formation = formationMapper.toEntity(request);
        formation.setStudent(student);
        formation = formationRepository.save(formation);

        FormationResponse response = formationMapper.toResponse(formation);

        return response;
    }

    @Override
    public FormationResponse getFormationById(UUID id) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found"));

        FormationResponse response = formationMapper.toResponse(formation);

        return response;
    }

    @Override
    public List<FormationResponse> getAllFormations() {
        return formationRepository.findAll().stream()
                .map(formationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FormationResponse updateFormation(UUID id, FormationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found"));
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        formationMapper.updateEntityFromRequest(request, formation);
        formation.setStudent(student);
        formation = formationRepository.save(formation);

        FormationResponse response = formationMapper.toResponse(formation);

        return response;
    }

    @Override
    public void deleteFormation(UUID id) {
        if (!formationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Formation not found");
        }
        formationRepository.deleteById(id);
    }
}