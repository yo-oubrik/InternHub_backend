package ma.ensa.pfaproject.web;

import ma.ensa.pfaproject.dto.InternshipStatisticsDTO;
import ma.ensa.pfaproject.entities.Internship;
import ma.ensa.pfaproject.entities.WorkMode;
import ma.ensa.pfaproject.service.CompanyService;
import ma.ensa.pfaproject.service.InternshipService;
import ma.ensa.pfaproject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    private final InternshipService internshipService;
    private final CompanyService companyService;
    private final StudentService studentService;

    @Autowired
    public AppController(InternshipService internshipService, CompanyService companyService, StudentService studentService) {
        this.internshipService = internshipService;
        this.companyService = companyService;
        this.studentService = studentService;
    }

    @GetMapping("/statistics")
    public InternshipStatisticsDTO getInternshipStatistics(){
        return internshipService.getInternshipStatistics();
    }
}
