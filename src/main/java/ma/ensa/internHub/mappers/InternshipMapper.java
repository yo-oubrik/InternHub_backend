package ma.ensa.internHub.mappers;

import ma.ensa.internHub.domain.dto.request.InternshipRequest;
import ma.ensa.internHub.domain.dto.response.InternshipResponse;
import ma.ensa.internHub.domain.entities.Internship;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = CompanyMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InternshipMapper {

    @Mapping(target = "id", ignore = true)
    Internship toEntity(InternshipRequest request);

    InternshipResponse toResponse(Internship internship);

    @Mapping(target = "id", ignore = true)
    void updateFromRequest(InternshipRequest request, @MappingTarget Internship internship);

}
