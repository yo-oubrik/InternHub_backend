package ma.ensa.internHub.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.services.InternshipService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping("/count/remote")
    public long countRemoteInternships() {
        return internshipService.countInternshipsByWorkMode("REMOTE");
    }


    @GetMapping("/count/on-site")
    public long countOnSiteInternships() {
        return internshipService.countInternshipsByWorkMode("ON_SITE");
    }

    @GetMapping("/count/all")
    public long countAllInternships() {
        return internshipService.countAllInternships();
    }
}