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
        HttpSecurity httpSecurity = http
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
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(PUBLIC_SWAGGER_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/students/count").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/students/count-by-month").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/companies/count-by-month").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/companies/count").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/internships/company/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/internships/count").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/internships/count/remote").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/v1/internships/count/on-site").permitAll()
                        .requestMatchers("/api/v1/password-reset/**").permitAll()
                        // Admin endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/students").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/companies").hasRole(Role.ADMIN.name())
                        // Student endpoints
                        .requestMatchers(HttpMethod.POST, "/api/v1/applications").hasRole(Role.STUDENT.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/applications").hasRole(Role.STUDENT.name())



                        // Company endpoints
                        .requestMatchers(HttpMethod.POST, "/api/v1/internships").hasRole(Role.COMPANY.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/internships/{id}").hasRole(Role.COMPANY.name())
                        .requestMatchers(HttpMethod.GET,"/api/v1/applications/company/{companyId}").hasRole(Role.COMPANY.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/applications/internship/{internshipId}").hasRole(Role.COMPANY.name())
                        .requestMatchers(HttpMethod.GET,"/api/v1/applications/company/{companyId}/count").hasRole(Role.COMPANY.name())
                        .requestMatchers(HttpMethod.GET,"/api/v1/applications/company/{companyId}/count/{status}").hasRole(Role.COMPANY.name())

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