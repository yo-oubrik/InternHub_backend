package ma.ensa.internHub.mappers;

import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.PendingCompany;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = { LocalDateTime.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PendingCompanyMapper {

    PendingCompany convertToPendingCompany(CompanyRequest request);

    @Mapping(target = "id", ignore = true)
    Company convertToCompany(PendingCompany pendingCompany);
}
