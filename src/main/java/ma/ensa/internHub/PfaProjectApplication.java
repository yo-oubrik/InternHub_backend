package ma.ensa.internHub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.SalaryType;
import ma.ensa.internHub.domain.entities.WorkMode;
import ma.ensa.internHub.repositories.CompanyRepository;
import ma.ensa.internHub.repositories.InternshipRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class PfaProjectApplication {

    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;

    public static void main(String[] args) {
        SpringApplication.run(PfaProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner start() {
        return args -> {
            Company company = new Company();
            company.setName("Example Company");
            company.setDescription("This is an example company description.");
            company.setAddress("123 Example Street, Example City");
            company.setIce("123456789012345");
            company.setEmail("example@company.com");
            company.setPassword("examplePassword");
            company.setCreatedAt(LocalDateTime.now());
            company.setUpdatedAt(LocalDateTime.now());
            companyRepository.save(company);

            Internship remoteInternship = new Internship();
            remoteInternship.setCreatedBy("User1");
            remoteInternship.setCompany(company);
            remoteInternship.setDescription("Remote internship description");
            remoteInternship.setDuration(6);
            remoteInternship.setSalary(1000);
            remoteInternship.setSalaryType(SalaryType.MONTH);
            remoteInternship.setDomain("IT");
            remoteInternship.setLocation("Remote");
            remoteInternship.setTitle("Remote Internship");
            remoteInternship.setWorkMode(WorkMode.REMOTE);
            remoteInternship.setTags(List.of());
            remoteInternship.setSkills(List.of("Java", "Spring Boot"));
            remoteInternship.setNegotiable(true);
            remoteInternship.setRemunerated(true);

            internshipRepository.save(remoteInternship);

            Internship onSiteInternship = new Internship();
            onSiteInternship.setCreatedBy("User2");
            onSiteInternship.setCompany(company);
            onSiteInternship.setDescription("On-site internship description");
            onSiteInternship.setDuration(6);
            onSiteInternship.setSalary(1200);
            onSiteInternship.setSalaryType(SalaryType.MONTH);
            onSiteInternship.setDomain("Marketing");
            onSiteInternship.setLocation("New York");
            onSiteInternship.setTitle("On-site Internship");
            onSiteInternship.setWorkMode(WorkMode.ON_SITE);
            onSiteInternship.setTags(List.of());
            onSiteInternship.setSkills(List.of("SEO", "Content Writing"));
            onSiteInternship.setNegotiable(false);
            onSiteInternship.setRemunerated(true);

            internshipRepository.save(onSiteInternship);
        };
    }
}