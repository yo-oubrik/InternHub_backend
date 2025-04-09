package ma.ensa.internHub.services.impl;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
import ma.ensa.internHub.domain.dto.response.AuthResponse;
import ma.ensa.internHub.domain.entities.PendingStudent;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.mappers.PendingStudentMapper;
import ma.ensa.internHub.repositories.PendingStudentRepository;
import ma.ensa.internHub.repositories.UserRepository;
import ma.ensa.internHub.services.AuthService;
import ma.ensa.internHub.services.EmailNotificationService;
import ma.ensa.internHub.utils.VerificationCodeGenerator;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PendingStudentRepository pendingStudentRepository;
    private final EmailNotificationService emailService;
    private final PendingStudentMapper pendingStudentMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;
    private final Long jwtExpiryMs = 864800L;

    @Override
    public UserDetails authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return userDetailsService.loadUserByUsername(email);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public UserDetails validateToken(String token) {
        String username = extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public AuthResponse createAuthResponse(UserDetails userDetails) {
        String token = generateToken(userDetails);
        // Calculate expiration timestamp (current time + 24 hours in milliseconds)
        long expirationTime = System.currentTimeMillis() + (86400 * 1000);

        return AuthResponse.builder()
                .token(token)
                .expiresIn(expirationTime)
                .build();
    }

    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public void initiateStudentVerification(StudentRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (pendingStudentRepository.existsByEmail(request.getEmail())) {
            PendingStudent pendingStudent = pendingStudentRepository.findByEmail(request.getEmail()).get();
            if (pendingStudent.getExpiryDate().isAfter(LocalDateTime.now())) {
                throw new DuplicateResourceException(
                        "Verification code already sent, please wait for 5 minutes before trying again");
            }
        }
        PendingStudent pendingStudent = pendingStudentMapper.convertToPendingStudent(request);
        pendingStudent.setPassword(passwordEncoder.encode(request.getPassword()));
        String confirmationCode = VerificationCodeGenerator.generateVerificationCode();
        pendingStudent.setConfirmationCode(confirmationCode);
        pendingStudentRepository.save(pendingStudent);

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("studentName", request.getFirstName() + " " + request.getLastName());
        templateModel.put("confirmationCode", confirmationCode);

        Map<String, String> inlineResources = new HashMap<>();
        inlineResources.put("logo.png", "/static/logo.png");

        emailService.sendHtmlEmail(
                request.getEmail(),
                "InternHub - Email Confirmation Code",
                "confirmation-code",
                templateModel,
                inlineResources);
    }

    @Override
    public boolean isVerificationInitiated(String email) {
        return pendingStudentRepository.existsByEmail(email);
    }

}
