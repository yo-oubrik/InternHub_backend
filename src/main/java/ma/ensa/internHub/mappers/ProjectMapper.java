package ma.ensa.internHub.mappers;

import ma.ensa.internHub.domain.dto.request.ProjectRequest;
import ma.ensa.internHub.domain.dto.response.ProjectResponse;
import ma.ensa.internHub.domain.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    Project toEntity(ProjectRequest request);

    ProjectResponse toResponse(Project project);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(ProjectRequest request, @MappingTarget Project project);
}