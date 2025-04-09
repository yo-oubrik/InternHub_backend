package ma.ensa.internHub.services;

import org.springframework.security.core.userdetails.UserDetails;

import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.AuthResponse;

public interface AuthService {
    UserDetails authenticate(String email, String password);

    String generateToken(UserDetails userDetails);

    UserDetails validateToken(String token);

    AuthResponse createAuthResponse(UserDetails userDetails);

    void initiateStudentVerification(StudentRequest request);

    boolean isVerificationInitiated(String email);
}
