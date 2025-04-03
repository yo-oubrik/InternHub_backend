package ma.ensa.internHub;

import ma.ensa.internHub.domain.entities.*;
import ma.ensa.internHub.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.repositories.CompanyRepository;
import ma.ensa.internHub.repositories.InternshipRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class PfaProjectApplication {

    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final StudentRepository studentRepository ;

    public static void main(String[] args) {
        SpringApplication.run(PfaProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner start() {
        return args -> {
            for (int i = 0; i < 10; i++) {
                Company company = new Company();
                company.setName("Company " + i);
                company.setDescription("Description for Company " + i);
                company.setAddress("Address for Company " + i);
                company.setIce(String.format("%015d", i)); // ICE number with leading zeros
                company.setEmail("company" + i + "@example.com");
                company.setPassword("securePassword");
                company.setCreatedAt(LocalDateTime.now());
                company.setUpdatedAt(LocalDateTime.now());

                companyRepository.save(company);
            }
        };
    }

//    @Bean
//    CommandLineRunner start() {
//        return args -> {
//            for(int i=0; i<10; i++){
//                Student student = new Student();
//                student.setFirstName("John");
//                student.setLastName("Doe");
//                student.setEmail("john.doe@example.com");
//                student.setPassword("securePassword");
////            student.setProfilePicture("http://example.com/profile.png"); // Valid URL format
//
//                student.setCreatedAt(LocalDateTime.now());
//                student.setUpdatedAt(LocalDateTime.now());
//                studentRepository.save(student);
//            }
//        };
//    }



//    @Bean
//    CommandLineRunner start() {
//        return args -> {
//            Student student = new Student();
//            student.setFirstName("John");
//            student.setLastName("Doe");
//            student.setEmail("john.doe@example.com");
//            student.setPassword("securePassword");
//            student.setProfilePicture("http://example.com/profile.png");
//            student.setEnrollmentDate(LocalDate.now());
//            student.setCreatedAt(LocalDateTime.now());
//            student.setUpdatedAt(LocalDateTime.now());
//
//            studentRepository.save(student);
//
//        };
//    }
}