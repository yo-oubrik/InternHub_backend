package ma.ensa.internHub.mappers;

import ma.ensa.internHub.domain.dto.request.ApplicationRequest;
import ma.ensa.internHub.domain.dto.response.ApplicationResponse;
import ma.ensa.internHub.domain.entities.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    Application toEntity(ApplicationRequest request);

    ApplicationResponse toResponse(Application application);

    @Mapping(target = "id", ignore = true)
    void updateFromRequest(ApplicationRequest request, @MappingTarget Application application);
}