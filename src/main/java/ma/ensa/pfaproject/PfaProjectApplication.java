package ma.ensa.pfaproject;

import ma.ensa.pfaproject.entities.*;
import ma.ensa.pfaproject.repositories.AdminRepository;
import ma.ensa.pfaproject.repositories.CompanyRepository;
import ma.ensa.pfaproject.repositories.InternshipRepository;
import ma.ensa.pfaproject.repositories.StudentRepository;
import ma.ensa.pfaproject.service.InternshipService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class PfaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PfaProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AdminRepository adminRepository,
                            StudentRepository studentRepository,
                            CompanyRepository companyRepository,
                            InternshipRepository internshipRepository,
                            InternshipService internshipService){
        return args ->{

            Stream.of("admin1","admin2", "admin3", "admin4", "admin5", "admin6")
                    .forEach(name->{
                        Admin admin1 = new Admin();
//                        admin1.setId(name);
                        admin1.setEmail("admin@gmail.com");
                        admin1.setFirstName(name);
                        admin1.setLastName("ben");
                        admin1.setRole(Role.ADMIN);
                        admin1.setProfilePicture("pic");
                        adminRepository.save(admin1);
                        //internshipService.saveAdmin(admin1);

                    });

           Stream.of("student1", "student2","student3")
                   .forEach(name->{
                       Student student1 = new Student();
                       student1.setId(name);
                       student1.setEmail("student@gmail.com");
                       student1.setFirstName(name);
                       student1.setLastName("ben");
                       student1.setRole(Role.STUDENT);
                       studentRepository.save(student1);
                   });

            Company company1 = new Company();
            company1.setAddress("company@gmail.com");
            company1.setId("co1");
            company1.setName("company1");
            company1.setRole(Role.COMPANY);
            companyRepository.save(company1);

            Company company2 = new Company();
            company2.setAddress("company@gmail.com");
            company2.setId("co2");
            company2.setName("company2");
            company2.setRole(Role.COMPANY);
            companyRepository.save(company2);

            Stream.of("internship1","internship2","internship3")
                    .forEach(name->{
                        Internship internship1 = new Internship();
                        internship1.setCompany(company1);
                        internship1.setDomain("info");
                        internship1.setSalaryType(SalaryType.MONTH);
                        internship1.setWorkMode(WorkMode.ON_SITE);
                        internship1.setTitle(name);
                        internshipRepository.save(internship1);
                    });

            Stream.of("internship4","internship5","internship6")
                    .forEach(name->{
                        Internship internship1 = new Internship();
                        internship1.setCompany(company2);
                        internship1.setDomain("Math");
                        internship1.setSalaryType(SalaryType.MONTH);
                        internship1.setWorkMode(WorkMode.ON_SITE);
                        internship1.setTitle(name);
                        //internshipRepository.save(internship1);
                        internshipService.saveInternship(internship1);
                    });


        };
    }

}
