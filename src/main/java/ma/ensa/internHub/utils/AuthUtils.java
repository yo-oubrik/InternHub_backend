package ma.ensa.internHub.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.entities.Company;
import ma.ensa.internHub.domain.entities.Student;
import ma.ensa.internHub.repositories.CompanyRepository;
import ma.ensa.internHub.repositories.StudentRepository;

@Component
@RequiredArgsConstructor
public class AuthUtils {
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public Student getCurrentStudent() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with email: " + email));
    }

    public Company getCurrentCompany() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return companyRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with email: " + email));
    }
}
