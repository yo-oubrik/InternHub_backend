package ma.ensa.internHub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.LoginRequest;
import ma.ensa.internHub.domain.dto.response.AuthResponse;
import ma.ensa.internHub.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = authService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponse.builder().token(token).expiresIn(864800).build();
        return ResponseEntity.ok(authResponse);
    }

}
