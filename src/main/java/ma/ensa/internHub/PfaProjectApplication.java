package ma.ensa.internHub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class PfaProjectApplication {

        public static void main(String[] args) {
                SpringApplication.run(PfaProjectApplication.class, args);
        }

        @Bean
        CommandLineRunner start() {
                return args -> {
                };
        };
}