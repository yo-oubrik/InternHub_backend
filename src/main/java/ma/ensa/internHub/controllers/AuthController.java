package ma.ensa.internHub.controllers;

import java.util.Base64;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.CompanyRequest;
import ma.ensa.internHub.domain.dto.request.EmailVerificationRequest;
import ma.ensa.internHub.domain.dto.request.LoginRequest;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.AuthResponse;
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
        AuthResponse authResponse = authService.createAuthResponse(userDetails);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/verify-email/students")
    public ResponseEntity<String> sendVerificationCode(@RequestBody @Valid StudentRequest request) {
        authService.initiateStudentVerification(request);
        return ResponseEntity.ok("Verification code sent to " + request.getEmail());
    }

    @PostMapping("/confirm/students")
    public ResponseEntity<String> confirmStudentEmail(@RequestBody @Valid EmailVerificationRequest request) {
        studentService.confirmAndRegisterStudent(request);
        return ResponseEntity.ok("Email verified successfully");
    }

    @GetMapping("/verification-status/students")
    public ResponseEntity<Boolean> checkVerificationStatus(@RequestParam String email) {
        String decodedEmail = new String(Base64.getDecoder().decode(email));
        return ResponseEntity.ok(authService.isVerificationInitiated(decodedEmail));
    }

    @PostMapping("/verify-email/companies")
    public ResponseEntity<String> registerCompany(@Valid @RequestBody CompanyRequest companyRequest) {
        authService.initiateCompanyVerification(companyRequest);
        return ResponseEntity.ok("Verification code sent to " + companyRequest.getEmail());
    }

    @PostMapping("/confirm/companies")
    public ResponseEntity<String> confirmCompanyEmail(@RequestBody @Valid EmailVerificationRequest request) {
        companyService.confirmAndRegisterCompany(request);
        return ResponseEntity.ok("Email verified successfully");
    }
}
