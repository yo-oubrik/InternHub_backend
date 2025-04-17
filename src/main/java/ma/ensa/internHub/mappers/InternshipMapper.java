package ma.ensa.internHub.mappers;

import ma.ensa.internHub.domain.dto.request.InternshipRequest;
import ma.ensa.internHub.domain.dto.response.InternshipResponse;
import ma.ensa.internHub.domain.entities.Internship;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = CompanyMapper.class)
public interface InternshipMapper {

    @Mapping(target = "id", ignore = true)
    Internship toEntity(InternshipRequest request);


    @Mapping(target = "companyResponse", ignore = true)
    InternshipResponse toResponse(Internship internship);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateFromRequest(InternshipRequest request, @MappingTarget Internship internship);

}
