package ma.ensa.internHub.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.ProjectRequest;
import ma.ensa.internHub.domain.dto.response.ProjectResponse;
import ma.ensa.internHub.domain.entities.Project;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.mappers.ProjectMapper;
import ma.ensa.internHub.repositories.ProjectRepository;
import ma.ensa.internHub.repositories.StudentRepository;
import ma.ensa.internHub.services.ProjectService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Student with email " + email + " not found"));

        Project project = projectMapper.toEntity(request);
        project.setStudent(student);
        project = projectRepository.save(project);
        student.getProjects().add(project);
        return projectMapper.toResponse(project);
    }

    @Override
    public ProjectResponse getProjectById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + id + " not found"));

        ProjectResponse response = projectMapper.toResponse(project);

        return response;
    }

    @Override
    public List<ProjectResponse> getProjectsByStudentId(UUID studentId) {
        return projectRepository.findByStudentId(studentId)
                .stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse updateProject(UUID id, ProjectRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + id + " not found"));

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("Student with ID " + request.getStudentId() + " not found"));

        projectMapper.updateEntityFromRequest(request, existingProject);
        existingProject.setStudent(student);

        existingProject = projectRepository.save(existingProject);

        ProjectResponse response = projectMapper.toResponse(existingProject);

        return response;
    }

    @Override
    public void deleteProject(UUID id) {
        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException("Project with ID " + id + " not found");
        }
        projectRepository.deleteById(id);
    }
}