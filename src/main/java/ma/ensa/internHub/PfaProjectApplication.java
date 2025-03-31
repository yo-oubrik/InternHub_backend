package ma.ensa.internHub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.services.StudentService;

@SpringBootApplication
@RequiredArgsConstructor
public class PfaProjectApplication {

    private final StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(PfaProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner start() {
        return args -> {
            for (int i = 0; i < 10; i++) {
                studentService.createStudent(StudentRequest.builder()
                    .firstName("FirstName" )
                    .lastName("LastName" )
                    .email("student" + i + "@example.com")
                    .password("password" )
                    .profilePicture(null)
                    .build());
            }
        };
    }
}