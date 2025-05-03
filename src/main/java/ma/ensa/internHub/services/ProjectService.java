package ma.ensa.internHub.services;

import ma.ensa.internHub.domain.dto.request.ProjectRequest;
import ma.ensa.internHub.domain.dto.response.ProjectResponse;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse getProjectById(UUID id);

    List<ProjectResponse> getProjectsByStudentId(UUID studentId);

    List<ProjectResponse> getAllProjects();

    ProjectResponse updateProject(UUID id, ProjectRequest request);

    void deleteProject(UUID id);
}