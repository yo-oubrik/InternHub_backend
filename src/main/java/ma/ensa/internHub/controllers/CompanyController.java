package ma.ensa.internHub.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;
import ma.ensa.internHub.services.CompanyService;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/count-by-month")
    public Map<String, Long> countCompaniesByMonth() {
        return companyService.countCompaniesByMonth();
    }

    @GetMapping("/count")
    public long countAllCompanies() {
        return companyService.countCompanies();
    }

    @GetMapping
    public List<CompanyResponse> getAllCompanies() {
        return companyService.getAllCompanies();
    }
}