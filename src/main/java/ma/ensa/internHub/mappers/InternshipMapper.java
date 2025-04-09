package ma.ensa.internHub.mappers;

import ma.ensa.internHub.domain.dto.request.InternshipRequest;
import ma.ensa.internHub.domain.dto.response.InternshipResponse;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.Internship;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = CompanyMapper.class)
public interface InternshipMapper {

    // Request DTO to Entity
    @Mapping(target = "id", ignore = true) // ID is generated
    @Mapping(target = "company.id", source = "request.companyId") // Map company ID to company entit
    Internship toEntity(InternshipRequest request);

    // Entity to Response DTO
    InternshipResponse toResponse(Internship internship);

    //Update existing entity from Request DTO
    @Mapping(target = "id", ignore = true) // ID should not be updated
    @Mapping(target = "company", ignore = true) // Company is managed separately
    void updateFromRequest(InternshipRequest request, @MappingTarget Internship internship);

}
