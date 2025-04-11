package ma.ensa.internHub.mappers;

import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.PendingCompany;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(
        componentModel = "spring",
        imports = { LocalDateTime.class },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PendingCompanyMapper {

    @Mapping(target = "expiryDate", expression = "java(LocalDateTime.now().plusMinutes(5))")
    PendingCompany convertToPendingCompany(CompanyRequest request);

    CompanyRequest convertToCompanyRequest(PendingCompany pendingCompany);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    Company convertToCompany(PendingCompany pendingCompany);
}
