package ma.ensa.internHub.mappers;

import ma.ensa.internHub.domain.dto.request.CertificatRequest;
import ma.ensa.internHub.domain.dto.response.CertificatResponse;
import ma.ensa.internHub.domain.entities.Certificat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CertificatMapper {

    @Mapping(target = "id", ignore = true)
    Certificat toEntity(CertificatRequest request);

    CertificatResponse toResponse(Certificat certificat);

    void updateEntityFromRequest(CertificatRequest request, @MappingTarget Certificat certificat);
}