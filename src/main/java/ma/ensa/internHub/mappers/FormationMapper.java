package ma.ensa.internHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ma.ensa.internHub.domain.dto.request.FormationRequest;
import ma.ensa.internHub.domain.dto.response.FormationResponse;
import ma.ensa.internHub.domain.entities.Formation;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FormationMapper {

    @Mapping(target = "id", ignore = true)
    Formation toEntity(FormationRequest request);

    FormationResponse toResponse(Formation formation);

    void updateEntityFromRequest(FormationRequest request, @MappingTarget Formation formation);
}