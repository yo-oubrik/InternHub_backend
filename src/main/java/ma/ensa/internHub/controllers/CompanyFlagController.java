package ma.ensa.internHub.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.services.CompanyFlagService;

@RestController
@RequestMapping("/api/v1/flagged-companies")
@RequiredArgsConstructor
public class CompanyFlagController {
    private final CompanyFlagService companyFlagService;

    @GetMapping("/count")
    public long unresolvedCompanyFlags() {
        return companyFlagService.countUnresolvedCompanyFlags();
    }
}
