package ma.ensa.internHub.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.services.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/count-by-month")
    public Map<String, Long> countCompaniesByMonth() {
        return companyService.countCompaniesByMonth();
    }
}