package ma.ensa.internHub.services.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.enums.ReportStatus;
import ma.ensa.internHub.repositories.CompanyFlagRepository;
import ma.ensa.internHub.services.CompanyFlagService;

@Service
@RequiredArgsConstructor
public class CompanyFlagServiceImpl implements CompanyFlagService {
    private final CompanyFlagRepository companyFlagRepository;

    @Override
    public long countUnresolvedCompanyFlags() {
        return companyFlagRepository.countByReportStatus(ReportStatus.UNRESOLVED);
    }

}
