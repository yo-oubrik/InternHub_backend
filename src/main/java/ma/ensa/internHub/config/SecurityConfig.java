package ma.ensa.internHub.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import ma.ensa.internHub.domain.enums.Role;
import ma.ensa.internHub.repositories.UserRepository;
import ma.ensa.internHub.security.JwtAuthFilter;
import ma.ensa.internHub.security.UserDetailsServiceImpl;
import ma.ensa.internHub.services.AuthService;

@Configuration
public class SecurityConfig {
    private static final String[] PUBLIC_SWAGGER_ENDPOINTS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
    };

    private static final String[] PUBLIC_API_ENDPOINTS = {
            "/api/v1/auth/**",
            "/api/v1/students/count",
            "/api/v1/students/count-by-month",
            "/api/v1/companies/count-by-month",
            "/api/v1/internships/**",
            "/api/v1/companies/count",
    };

    private static final String[] ADMIN_ONLY_ENDPOINTS = {
            "/api/v1/students",
            "/api/v1/companies",
            "/api/v1/students/{id}",
            "/api/v1/companies/{id}",
            "/api/v1/company-flags/**"
    };

    private static final String[] STUDENT_ONLY_ENDPOINTS = {
            "/api/v1/students/email/{email}",
            "/api/v1/students/{id}"
    };

    @Bean
    public JwtAuthFilter jwtAuthFilter(AuthService authService) {
        return new JwtAuthFilter(authService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                        .requestMatchers(PUBLIC_SWAGGER_ENDPOINTS).permitAll()
                        .requestMatchers(PUBLIC_API_ENDPOINTS).permitAll()
                        .requestMatchers(ADMIN_ONLY_ENDPOINTS).hasRole(Role.ADMIN.name())
                        .requestMatchers(STUDENT_ONLY_ENDPOINTS).hasRole(Role.STUDENT.name())

                        // Secure everything else
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}