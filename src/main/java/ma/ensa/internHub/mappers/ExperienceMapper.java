package ma.ensa.internHub.mappers;

import ma.ensa.internHub.domain.dto.request.ExperienceRequest;
import ma.ensa.internHub.domain.dto.response.ExperienceResponse;
import ma.ensa.internHub.domain.entities.Experience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExperienceMapper {

    @Mapping(target = "id", ignore = true)
    Experience toEntity(ExperienceRequest request);

    ExperienceResponse toResponse(Experience experience);

    void updateEntityFromRequest(ExperienceRequest request, @MappingTarget Experience experience);
}