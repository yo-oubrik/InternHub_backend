package ma.ensa.internHub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.request.LoginRequest;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.AuthResponse;
import ma.ensa.internHub.domain.dto.response.CompanyResponse;
import ma.ensa.internHub.domain.dto.response.StudentResponse;
import ma.ensa.internHub.services.AuthService;
import ma.ensa.internHub.services.CompanyService;
import ma.ensa.internHub.services.StudentService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final StudentService studentService;
    private final CompanyService companyService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = authService.generateToken(userDetails);
        // Calculate expiration timestamp (current time + 24 hours in milliseconds)
        long expirationTime = System.currentTimeMillis() + (86400 * 1000);
        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .expiresIn(expirationTime)
                .build();
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register/students")
    public ResponseEntity<StudentResponse> registerStudent(@RequestBody @Valid StudentRequest request) {
        return ResponseEntity.ok(studentService.createStudent(request));
    }

    @PostMapping("/register/companies")
    public ResponseEntity<CompanyResponse> registerCompany(@RequestBody @Valid CompanyRequest request) {
        return ResponseEntity.ok(companyService.createCompany(request));
    }
}
