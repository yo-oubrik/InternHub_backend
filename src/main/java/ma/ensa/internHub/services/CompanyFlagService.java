package ma.ensa.internHub.services;

import java.util.List;
import java.util.UUID;

import ma.ensa.internHub.domain.dto.request.NotificationRequest;
import ma.ensa.internHub.domain.dto.request.CreateFlagRequest;
import ma.ensa.internHub.domain.dto.response.CompanyFlagResponse;
import ma.ensa.internHub.domain.dto.response.FlaggedCompanyOverview;

public interface CompanyFlagService {
    void createCompanyFlag(CreateFlagRequest request);

    long countUnresolvedCompanyFlags();

    List<FlaggedCompanyOverview> getFlaggedCompaniesOverview();

    long getUnresolvedCompanyFlagsCountById(UUID id);

    long getResolvedFlagsCountByCompanyId(UUID id);

    long getCompanyWarningsCount(UUID id);

    List<CompanyFlagResponse> getCompanyFlagsHistory(UUID id);

    void ignoreCompanyFlag(UUID id);

    void warnCompany(UUID id, NotificationRequest request);
}
