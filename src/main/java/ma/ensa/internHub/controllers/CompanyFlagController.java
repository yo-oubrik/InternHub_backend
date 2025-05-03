package ma.ensa.internHub.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.NotificationRequest;
import ma.ensa.internHub.domain.dto.response.CompanyFlagResponse;
import ma.ensa.internHub.domain.dto.response.FlaggedCompanyOverview;
import ma.ensa.internHub.services.CompanyFlagService;

@RestController
@RequestMapping("/api/v1/flagged-companies")
@RequiredArgsConstructor
public class CompanyFlagController {
    private final CompanyFlagService companyFlagService;

    @GetMapping("/count")
    public long countUnresolvedCompanyFlags() {
        return companyFlagService.countUnresolvedCompanyFlags();
    }

    @GetMapping("/{id}/flags")
    public List<CompanyFlagResponse> getCompanyFlagsHistory(@PathVariable UUID id) {
        return companyFlagService.getCompanyFlagsHistory(id);
    }

    @GetMapping("/overview")
    public List<FlaggedCompanyOverview> getFlaggedCompaniesOverview() {
        return companyFlagService.getFlaggedCompaniesOverview();
    }

    @GetMapping("/{id}/unresolved/count")
    public long getUnresolvedCompanyFlagsCountById(@PathVariable UUID id) {
        return companyFlagService.getUnresolvedCompanyFlagsCountById(id);
    }

    @GetMapping("/{id}/resolved/count")
    public long getResolvedCompanyFlagsCountById(@PathVariable UUID id) {
        return companyFlagService.getResolvedFlagsCountByCompanyId(id);
    }

    @GetMapping("/{id}/warnings/count")
    public long getCompanyWarningsCount(@PathVariable UUID id) {
        return companyFlagService.getCompanyWarningsCount(id);
    }

    @PutMapping("/{id}/resolve")
    public void ignoreCompanyFlag(@PathVariable UUID id) {
        companyFlagService.ignoreCompanyFlag(id);
    }

    @PostMapping("/{id}/warn")
    public void warnCompany(@PathVariable UUID id, @ModelAttribute NotificationRequest request) {
        companyFlagService.warnCompany(id, request);
    }
}
