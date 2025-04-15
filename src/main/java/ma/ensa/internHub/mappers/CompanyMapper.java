package ma.ensa.internHub.mappers;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;
import ma.ensa.internHub.domain.entities.Company;

@Mapper(componentModel = "spring", imports = LocalDateTime.class , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    Company toEntity(CompanyRequest request);

    CompanyResponse toResponse(Company company);

    @Mapping(target = "id", ignore = true)
    void updateFromRequest(CompanyRequest request, @MappingTarget Company company);
}